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
import { useCookies } from "react-cookie";
import { BoardRegisterApi } from "../../../apis/boardApis";

interface WriteBoardProps {
  onMainClick: () => void;
  currentPage: string;
}
export default function WriteBoard({
  onMainClick,
}: WriteBoardProps) {
  const [boardTitle, setBoardTitle] = useState<string>("");
  const [boardContent, setBoardContent] = useState<string>("");
  const [boardImage, setBoardImage] = useState<File | null>(null);
  const [boardVideo, setBoardVideo] = useState<File | null>(null);
  const [boardFile, setBoardFile] = useState<File | null>(null);
  const { user } = useUserStore();
  const [cookies] = useCookies();

  const registerHandler = async () => {
    const data = new FormData();
    data.append("boardTitle", boardTitle);
    data.append("boardContent", boardContent);
    data.append("boardWriterEmail", user.userEmail);
    data.append("boardWriterProfile", user.userProfile);
    data.append("boardWriterNickname", user.userNickname);
    data.append("boardWriteDate", new Date().toISOString());
    const token = cookies.token;

    if (boardImage){
      data.append("boardImage",boardImage);
    }
    if (boardVideo){
      data.append("boardVideo",boardVideo);
    }
    if (boardFile){
      data.append("boardFile",boardFile);
    }
    
    const uploadReponse = await BoardRegisterApi(data,token);


    if (!uploadReponse) {
      alert("게시글 작성(파일)에 실패했습니다.");
      return;
    }
    if (!uploadReponse.result) {
      alert("게시글 작성(파일)에 실패했습니다.");
      return;
    }

    alert("게시글 작성에 성공했습니다!");

    onMainClick();
    
  };

  const handleImgUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files && e.target.files[0];
    setBoardImage(file || null);
    console.log(file)
  };

  const handleVideoUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files && e.target.files[0];
    setBoardVideo(file || null);
    console.log(file)
  };

  const handleFileUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files && e.target.files[0];
    setBoardFile(file || null);
    console.log(file)
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
              onChange={handleImgUpload}
            />
          </Button>
          <Button variant="contained" component="label">
            비디오 첨부
            <input
              type="file"
              hidden
              onChange={handleVideoUpload}
            />
          </Button>
          <Button variant="contained" component="label">
            파일 첨부
            <input
              type="file"
              hidden
              onChange={handleFileUpload}
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
