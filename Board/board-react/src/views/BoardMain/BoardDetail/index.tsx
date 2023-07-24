import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { Board } from "../../../interfaces";
import { BoardApi } from "../../../apis";
import { Box, Button, Card, CardContent, CardMedia, TextField, Typography } from "@mui/material";
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
  const [cookies] = useCookies();
  useEffect(() => {
    async function fetchData() {
      try {
        const token = cookies.token;
        const response = await BoardApi(token,boardNumber);
        const data = response.data;
        setBoardData(data);
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setBoardData(undefined);
      }
    }
    fetchData();

  }, [boardNumber,cookies.token]); // Run only once on component mount

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
        <Typography variant="body2" color="text.secondary" gutterBottom>
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
      </CardContent>
    </Card>
    </Box>
    <Box marginTop="20px">
        <TextField
          id="comment"
          label="댓글 작성"
          variant="outlined"
          fullWidth
          // Add your onChange and value handlers here for handling user input
        />
        <Button
          variant="contained"
          color="primary"
          sx={{ marginTop: "10px" }}
          // Add your onClick handler here for submitting the comment
        >
          댓글 작성
        </Button>
      </Box>

      {/* {댓글 목록 }
      <CommentSection
        // Pass any required props to the CommentSection component
      /> */}

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
