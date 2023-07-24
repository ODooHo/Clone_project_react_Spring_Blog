import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { useUserStore } from "../../../stores";
import { BoardRegisterApi } from "../../../apis";
import { useCookies } from "react-cookie";
import { MarginRounded } from "@mui/icons-material";

interface WriteBoardProps {
  onMainClick: () => void;
  currentPage: string;
}
export default function WriteBoard({
  onMainClick,
  currentPage,
}: WriteBoardProps) {
  const [boardTitle, setBoardTitle] = useState<string>("");
  const [boardContent, setBoardContent] = useState<string>("");
  const [boardImage, setBoardImage] = useState<string>("");
  const [boardVideo, setBoardVideo] = useState<string>("");
  const [boardFile, setBoardFile] = useState<string>("");
  const { user } = useUserStore();
  const [cookies] = useCookies();

  const registerHandler = async () => {
    const data = {
      boardTitle,
      boardContent,
      boardImage,
      boardVideo,
      boardFile,
      boardWriterEmail: user.userEmail, // 사용자 이메일
      boardWriterProfile: user.userProfile, // 사용자 프로필
      boardWriterNickname: user.userNickname, // 사용자 닉네임
      boardWriteDate: new Date().toISOString(), // 글을 쓴 날짜
    };

    const token = cookies.token;
    const registerResponse = await BoardRegisterApi(data, token);
    if (!registerResponse) {
      alert("게시글 작성에 실패했습니다.");
      return;
    }
    if (!registerResponse.result) {
      alert("게시글 작성에 실패했습니다.");
      return;
    }

    alert("게시글 작성에 성공했습니다!");

    onMainClick();
    
  };

  return (
    <>
      <Card sx={{ padding: 5, marginTop: "50px" }}>
        <CardContent>
          <Typography variant="h5" marginBottom={"10px"}>게시글 작성</Typography>
          <TextField
            label="제목"
            fullWidth
            value={boardTitle}
            onChange={(e) => setBoardTitle(e.target.value)}
          />
          <Box marginTop="10px">
          <TextField
            label="내용"
            fullWidth
            multiline
            rows={6}
            value={boardContent}
            onChange={(e) => setBoardContent(e.target.value)}
          />
          </Box>
        </CardContent>
        <CardActions>
          <Button variant="contained" component="label">
            사진 첨부
            <input
              type="file"
              hidden
              onChange={(e) => setBoardImage(e.target.value)}
            />
          </Button>
          <Button variant="contained" component="label">
            비디오 첨부
            <input
              type="file"
              hidden
              onChange={(e) => setBoardVideo(e.target.value)}
            />
          </Button>
          <Button variant="contained" component="label">
            파일 첨부
            <input
              type="file"
              hidden
              onChange={(e) => setBoardFile(e.target.value)}
            />
          </Button>
        </CardActions>
      </Card>
      <Button
        variant="contained"
        color="inherit"
        sx={{ margin: "10px", backgroundColor: "#000000", color: "#ffffff" }}
        onClick={registerHandler}
      >
        작성 완료
      </Button>

      <Button
        variant="contained"
        color="inherit"
        sx={{ margin: "10px", backgroundColor: "#ffffff", color: "#000000" }}
        onClick={onMainClick}
      >
        취소
      </Button>
    </>
  );
}
