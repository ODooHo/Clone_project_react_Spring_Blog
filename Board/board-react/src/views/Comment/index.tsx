import { Box, Button, TextField, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import {Comment} from '/Users/oduho/Desktop/Clone_SpringBoot-react-aws_Blog/Board/board-react/src/interfaces'
import { CommentListApi, CommentRegisterApi } from '../../apis';
import { useCookies } from 'react-cookie';
import { useUserStore } from '../../stores';



interface CommentMainProps{
  boardNumber :number;
}

export default function CommentMain(
  {boardNumber}
 :CommentMainProps){
  const [comments, setComments] = useState<Comment[]>([]);
  const [commentContent, setCommentContent] = useState<string>('');
  const [cookies,setCookies] = useCookies();
  const { user } = useUserStore();
 
 
  const getCommentHandler = async () => {
    try {
      const token = cookies.token;
      const response = await CommentListApi(token,boardNumber);
      const data = response.data;
      setComments(data);
    } catch (error) {
      console.error("게시글 가져오기 실패:", error);
      setComments([]); // 실패 시 빈 배열로 초기화
    }
  };

  // 페이지가 변경될 때마다 API를 호출하도록 useEffect 사용
  useEffect(() => {
    getCommentHandler();
  }, []);


  const CommentRegisterHandler = async () => {
    const token = cookies.token;
    const registerData = {
      boardNumber,
      commentContent,
      userEmail: user.userEmail,
      commentUserProfile: user.userProfile,
      commentUserNickname : user.userNickname,
      commentWriteDate: new Date().toISOString(),
    }

    const response = await CommentRegisterApi(registerData,token,boardNumber);
    if (!response) {
      alert("댓글 작성에 실패했습니다.");
      return;
    }
    if (!response.result) {
      alert("댓글 작성에 실패했습니다.");
      return;
    }
    alert("댓글 작성에 성공했습니다!");

    //window.location.reload();
  };




  return (
    <>      
    {comments.map((comment) => (
        <Box key={comment.commentId} mt={2} p={2} border="1px solid #ddd" borderRadius={4}>
          <Typography variant="subtitle1">
            {comment.commentUserNickname} | {comment.commentWriteDate}
          </Typography>
          <Typography variant="body1">{comment.commentContent}</Typography>
        </Box>
      ))}
    <Box marginTop="20px">
      <TextField
        id="comment"
        label="댓글 작성"
        variant="outlined"
        fullWidth
        onChange={(e) => setCommentContent(e.target.value)}
      />
      <Button variant="contained" color="primary" sx={{ marginTop: '10px' }}
      onClick = {CommentRegisterHandler}
      >
        댓글 작성
      </Button>
    </Box>
    </>
  );
}
