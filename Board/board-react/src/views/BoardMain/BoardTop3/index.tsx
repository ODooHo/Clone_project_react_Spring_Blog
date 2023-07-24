import { useEffect, useState } from "react";
import { Box, Button, Card, Divider, Grid, Typography } from "@mui/material";
import axios from "axios";
import { useCookies } from "react-cookie";
import { useUserStore } from "../../../stores";
import { BoardTop3Api } from "../../../apis";
import { Board } from "../../../interfaces";

// 인터페이스를 정의합니다.

interface BoardTop3Props{
  onDetailClick:(boardId:number) => void;
}

export default function BoardTop3({
  onDetailClick
}: BoardTop3Props) {
  // authView : true - signUp / false - signIn
  const [boardData, setBoardData] = useState<Board[]>([]); // 인터페이스를 적용하여 배열의 요소를 정확히 타입화합니다.
  const [boardTitle, setBoardTitle] = useState<string>("");
  const [boardContent, setBoardContent] = useState<string>("");

  const [cookies, setCookies] = useCookies();
  const { user, setUser } = useUserStore();

  useEffect(() => {
    async function fetchData() {
      try {
        const token = cookies.token;
        const response = await BoardTop3Api(token);
        const data = response.data;
        setBoardData(data);
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setBoardData([]);
      }
    }
    fetchData();
  }, []); // Run only once on component mount
  return (
    <>
      <div
        style={{
          maxHeight: "100vh",
          padding: "5px",
          marginTop: "100px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center", // 화면 가운데 정렬
        }}
      >
        <Typography variant="h5" align="center" gutterBottom>
          주간 Top3 게시글
        </Typography>
        <Divider />
        <Box
          display="flex"
          flexDirection="row"
          justifyContent="center"
          alignItems="center"
          flexWrap="wrap" // 글이 화면 너비를 초과할 경우 자동으로 다음 줄로 넘김
        >
          {boardData.map((board) => (
            <Button
              key={board.boardNumber}
              variant="outlined" // 배경색은 투명하고 테두리만 보이도록 변경
              color="inherit" // 글자색을 검정색으로 변경
              sx={{
                width: "200px",
                height: "200px",
                margin: "10px",
              }}
              onClick={() => onDetailClick(board.boardNumber)}
            >
              <Typography variant="h6" sx={{ marginRight: "10px" }}>
                {board.boardTitle}
              </Typography>
              <Typography>{board.boardWriterNickname}</Typography>
            </Button>
          ))}
        </Box>
      </div>
    </>
  );
}
