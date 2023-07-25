import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { Board } from "../../../interfaces";
import { BoardApi, LikyApi, LikyRegisterApi } from "../../../apis";
import { Box, Button, Card, CardContent, CardMedia, TextField, Typography } from "@mui/material";
import CommentMain from "../../Comment";
import { Cookie } from "@mui/icons-material";
import { useUserStore } from "../../../stores";
interface BoardDetailProps {
  onMainClick: () => void;
  currentPage: string;
  boardNumber: number; // 게시물 ID를 받아오도록 추가
}

export default function BoardDetail({
  onMainClick,
  currentPage,
  boardNumber
}: BoardDetailProps) {
  const [boardData, setBoardData] = useState<Board |undefined>(undefined);
  const [cookies,setCookies] = useCookies();
  const [liked, setLiked] = useState<boolean>(false);
  const [state,setCanLike] = useState();
  const {user} = useUserStore();
  const token = cookies.token;
  useEffect(() => {
    async function fetchData() {
      try {
        const response = await BoardApi(token,boardNumber);
        const data = response.data;
        setBoardData(data);
        const response_2 = await LikyApi(token,boardNumber);
        const data_2 = response_2.data;
        setCanLike(data_2);
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setBoardData(undefined);
        setCanLike(undefined);
      }
    }
    fetchData();

  }, [boardNumber,token]); // Run only once on component mount




  const handleLikeClick = async () => {
    const likeUserdata = {
      boardNumber,
      userEmail : user.userEmail,
      likeUserProfile: user.userProfile,
      likeUserNickname : user.userNickname
    }
    // 좋아요 버튼을 누를 때 호출되는 함수
    try{
      const response = await LikyRegisterApi(likeUserdata, token, boardNumber);
      setLiked(response); // 서버에서 좋아요 여부를 다시 받아와서 업데이트    
    }catch(error){
      console.error("좋아요 가져오기 실패:", error);
      setLiked(false);
    }
  };

  const handleUnlikeClick = async () => {
    // 좋아요 취소 버튼을 누를 때 호출되는 함수
    //const response = await LikeApi(token, boardNumber);
    //setLiked(response); // 서버에서 좋아요 여부를 다시 받아와서 업데이트
  };

  if (!boardData) {
    return <div>로딩 중...</div>;
  }


  const {
    boardTitle,
    boardContent,
    boardImage,
    boardVideo,
    boardFile,
    boardWriterProfile,
    boardWriterNickname,
    boardWriteDate,
    boardClickCount,
    boardLikeCount,
    boardCommentCount,
  } = boardData;



  return (
    <>
    <Box marginTop="70px">
    <Card>
      <CardContent>
        <Typography variant="h5" gutterBottom>
          {boardTitle}
        </Typography>
        <Typography variant="body1" gutterBottom>
          {boardWriterNickname} | {boardWriteDate}
        </Typography>
        <Typography variant="body2" color="text.secondary" gutterBottom sx={{ fontSize: "1.2rem", lineHeight: "1.8rem" }}>
              {boardContent}
            </Typography>
        <Box my={2}>
          {/* 게시물 이미지를 보여줄 경우 */}
          {boardImage && (
            <CardMedia
              component="img"
              height="auto"
              image={boardImage}
              alt="게시물 이미지"
            />
          )}
          {/* 게시물 동영상을 보여줄 경우 */}
          {boardVideo && (
            <iframe
              title="게시물 동영상"
              width="100%"
              height="315"
              src={boardVideo}
              allowFullScreen
            ></iframe>
          )}
          {/* 게시물 파일을 다운로드 링크로 보여줄 경우 */}
          {boardFile && (
            <a href={boardFile} download>
              게시물 파일 다운로드
            </a>
          )}
        </Box>
        <Typography variant="body2" gutterBottom>
          조회수: {boardClickCount} | 좋아요: {boardLikeCount} | 댓글 수:{" "}
          {boardCommentCount}
        </Typography>
        {liked ? (
              // 사용자가 좋아요를 눌렀을 경우, 좋아요 취소 버튼 표시
              <Button
                variant="contained"
                color="primary"
                onClick={handleUnlikeClick}
              >
                좋아요 취소
              </Button>
            ) : (
              // 사용자가 좋아요를 누르지 않았을 경우, 좋아요 버튼 표시
              <Button
                variant="contained"
                color="primary"
                onClick={handleLikeClick}
              >
                좋아요
              </Button>
            )}
      </CardContent>
    </Card>
    </Box>
      <CommentMain boardNumber = {boardNumber}/>

    <Button
        variant="contained"
        color="inherit"
        sx={{ margin: "10px", backgroundColor: "#ffffff", color: "#000000" }}
        onClick={onMainClick}
      >
        이전
      </Button>
  </>
  );
}
