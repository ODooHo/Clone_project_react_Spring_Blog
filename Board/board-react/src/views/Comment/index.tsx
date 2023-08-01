import { Box, Button, Hidden, TextField, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Comment } from "/Users/oduho/Desktop/Clone_SpringBoot-react-aws_Blog/Board/board-react/src/interfaces";
import { useCookies } from "react-cookie";
import { useUserStore } from "../../stores";
import {
  CommentListApi,
  CommentRegisterApi,
  deleteCommentApi,
} from "../../apis/commentApis";

interface CommentMainProps {
  boardNumber: number;
}

export default function CommentMain({ boardNumber }: CommentMainProps) {
  const [comments, setComments] = useState<Comment[]>([]);
  const [commentContent, setCommentContent] = useState<string>("");
  const [cookies, setCookies] = useCookies();
  const { user } = useUserStore();
  const [refresh, setRefresh] = useState(1);


  // 페이지가 변경될 때마다 API를 호출하도록 useEffect 사용
  useEffect(
    () => {
      async function fetchData(){
      try {
        const token = cookies.token;
        const response = await CommentListApi(token, boardNumber);
        const data = response.data;
        setComments(data);
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setComments([]); // 실패 시 빈 배열로 초기화
      }
    }
    fetchData();
  }, [refresh]);

  const CommentRegisterHandler = async () => {
    const token = cookies.token;
    const registerData = {
      boardNumber,
      commentContent,
      userEmail: user.userEmail,
      commentUserProfile: user.userProfile,
      commentUserNickname: user.userNickname,
      commentWriteDate: new Date().toISOString(),
    };

    const handleRefresh = () => {
      setRefresh(refresh * -1); // refresh 값을 변경하여 컴포넌트를 새로고침
    };
    const response = await CommentRegisterApi(registerData, token, boardNumber);
    if (!response) {
      alert("댓글 작성에 실패했습니다.");
      return;
    }
    if (!response.result) {
      alert("댓글 작성에 실패했습니다.");
      return;
    }
    alert("댓글 작성에 성공했습니다!");

    handleRefresh();
  };

  const handleDeleteClick = async (commentId: number) => {
    try {
      const token = cookies.token;
      const response = await deleteCommentApi(token, boardNumber, commentId);
      console.log(response);
      if (response && response.result) {
        alert("댓글이 삭제되었습니다.");
        setComments((prevComments) =>
          prevComments.filter((comment) => comment.commentId !== commentId)
        );
      } else {
        alert("댓글 삭제에 실패했습니다.");
      }
    } catch (error) {
      console.error("댓글 삭제 실패:", error);
    }
  };

  return (
    <>
    <Box sx={{marginTop : "20px"}}>

    </Box>
      {comments.map((comment) => (
        <Box
          key={comment.commentId}
          mt={2}
          p={2}
          border="1px solid #ddd"
          borderRadius={3}
          display="flex"
          flexDirection="column" // 닉네임과 날짜, 내용을 세로 방향으로 배치
          alignItems="flex-start" // 왼쪽으로 정렬
          sx={{ maxWidth: 1100, width: "100%", margin: "0 auto",marginTop : "20px" ,marginBottom : "5px"}}
        >
          <Box
           width={28}
           height={28}
           borderRadius="50%"
           overflow="hidden"
           mr={1}
          >
            <img
            src={`http://localhost:4000/api/images/${comment.commentUserProfile}`}
            width="100%"
            height="100%"      
            />

          </Box>
          <Typography variant="subtitle1">
            {comment.commentUserNickname} | {comment.commentWriteDate}
          </Typography>
          <Box
            display="flex"
            justifyContent="space-between"
            alignItems="center"
          >
            <Typography variant="body1">{comment.commentContent}</Typography>
            {comment.userEmail === user?.userEmail && ( // Only show delete button if the logged-in user wrote the comment
              <Typography
                variant="body2"
                color="primary"
                sx={{ cursor: "pointer", color: "red" }}
                onClick={() => handleDeleteClick(comment.commentId)}
              >
                댓글 삭제
              </Typography>
            )}
          </Box>
        </Box>
      ))}
      <Box
       sx={{ maxWidth: 1100, width: "100%", margin: "0 auto" ,marginTop:"20px" }}
       
      >
        <TextField
          id="comment"
          label="댓글 작성"
          variant="outlined"
          fullWidth
          onChange={(e) => setCommentContent(e.target.value)}
        />
        <Button
          variant="contained"
          color="primary"
          sx={{ marginTop: "10px" }}
          onClick={CommentRegisterHandler}
        >
          댓글 작성
        </Button>
      </Box>
    </>
  );
}
