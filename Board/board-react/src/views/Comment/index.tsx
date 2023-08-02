import { Box, Button, Hidden, TextField, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { Comment } from "/Users/oduho/Desktop/Clone_SpringBoot-react-aws_Blog/Board/board-react/src/interfaces";
import { useCookies } from "react-cookie";
import { useUserStore } from "../../stores";
import {
  CommentListApi,
  CommentRegisterApi,
  deleteCommentApi,
  editCommentApi,
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
  const [editStates, setEditStates] = useState<{ [key: number]: boolean }>({});

  // 페이지가 변경될 때마다 API를 호출하도록 useEffect 사용
  useEffect(() => {
    async function fetchData() {
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

  const handleRefresh = () => {
    setRefresh(refresh * -1); // refresh 값을 변경하여 컴포넌트를 새로고침
  };

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

  const handleEditClick = (commentId: number) => {
    setEditStates((prevEditStates) => ({
      ...prevEditStates,
      [commentId]: true,
    }));
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

  const handleEditHandler = async (
    commentId: number,
    editedContent: string
  ) => {
    try {
      const token = cookies.token;
      const data = {
        commentContent: editedContent,
        commentWriteDate: new Date().toISOString(),
      };
      const response = await editCommentApi(
        data,
        token,
        boardNumber,
        commentId
      );
      if (response && response.result) {
        alert("댓글이 수정되었습니다.");
        setEditStates((prevEditStates) => ({
          ...prevEditStates,
          [commentId]: false,
        }));
        handleRefresh();
      } else {
        alert("댓글 수정에 실패했습니다.");
      }
    } catch (error) {
      console.error("댓글 수정 실패:", error);
    }
  };

  return (
    <>
      <Box sx={{ marginTop: "20px" }}></Box>
      <Box
        sx={{
          maxWidth: 900,
          width: "100%",
          margin: "0 auto",
          marginTop: "20px",
        }}
      >
        <TextField
          id="comment"
          label="댓글 작성"
          variant="outlined"
          fullWidth
          onChange={(e) => setCommentContent(e.target.value)}
        />

        <Box
          display="flex"
          justifyContent="flex-end"
          sx={{
            maxWidth: 900,
            width: "100%",
            margin: "10px auto",
          }}
        >
          <Button
            variant="contained"
            color="primary"
            sx={{ marginBottom: "50px" }}
            onClick={CommentRegisterHandler}
          >
            댓글 작성
          </Button>
        </Box>
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
          sx={{
            maxWidth: 900,
            width: "100%",
            margin: "0 auto",
            marginTop: "20px",
            marginBottom: "5px",
          }}
        >
          <Box display="flex" alignItems="center" width="100%">
            <Box
              width={32}
              height={32}
              borderRadius="50%"
              overflow="hidden"
              mr={1} // 이미지와 닉네임 사이의 간격을 설정합니다.
            >
              <img
                src={`http://localhost:4000/api/images/${comment.commentUserProfile}`}
                width="100%"
                height="100%"
              />
            </Box>
            <Box width="100%">
              <Box
                display="flex"
                justifyContent="space-between"
                alignItems="center"
              >
                <Typography
                  variant="body1"
                  gutterBottom
                  marginTop={"20px"}
                  marginBottom="2px"
                >
                  {comment.commentUserNickname}
                </Typography>
                {comment.userEmail === user?.userEmail && ( // Only show delete button if the logged-in user wrote the comment
                  <>
                    <Box display="flex" alignItems="center">
                      <Typography
                        variant="body2"
                        color="primary"
                        sx={{
                          cursor: "pointer",
                          color: "black",
                          marginRight: "20px",
                          "&:hover": {
                            textDecoration: "underline", // Add underline effect on hover
                          },
                        }}
                        onClick={() => handleEditClick(comment.commentId)}
                      >
                        수정
                      </Typography>
                      <Typography
                        variant="body2"
                        color="primary"
                        sx={{
                          cursor: "pointer",
                          color: "black",
                          "&:hover": {
                            textDecoration: "underline", // Add underline effect on hover
                          },
                        }}
                        onClick={() => handleDeleteClick(comment.commentId)}
                      >
                        삭제
                      </Typography>
                    </Box>
                  </>
                )}
              </Box>
              <Typography variant="body2" color="text.secondary">
                {comment.commentWriteDate}
              </Typography>
            </Box>
          </Box>
          <Typography variant="body1" marginTop={"10px"}>
            {editStates[comment.commentId] ? (
              <>
                <TextField
                  id={`comment-${comment.commentId}`}
                  label="댓글 수정"
                  variant="outlined"
                  fullWidth
                  defaultValue={comment.commentContent}
                />
                <Button
                  variant="contained"
                  color="primary"
                  sx={{ marginTop: "10px" }}
                  onClick={() => {
                    const editedContent = (
                      document.getElementById(
                        `comment-${comment.commentId}`
                      ) as HTMLInputElement
                    )?.value;
                    handleEditHandler(comment.commentId, editedContent);
                  }}
                >
                  수정 완료
                </Button>
              </>
            ) : (
              comment.commentContent
            )}
          </Typography>
        </Box>
      ))}
    </>
  );
}
