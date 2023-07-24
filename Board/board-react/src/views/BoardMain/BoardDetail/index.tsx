import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { Board } from "../../../interfaces";
import { BoardApi } from "../../../apis";
import { Box, Card, CardContent, CardMedia, Typography } from "@mui/material";
interface BoardDetailProps {
  onMainClick: () => void;
  currentPage: string;
  boardNumber:number;
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
  </>
  );
}
