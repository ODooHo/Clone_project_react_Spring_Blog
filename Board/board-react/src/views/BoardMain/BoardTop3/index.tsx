import { useEffect, useState } from "react";
import { Box, Button, Divider, Typography } from "@mui/material";
import { useCookies } from "react-cookie";
import { useUserStore } from "../../../stores";
import { Board } from "../../../interfaces";
import { BoardTop3Api } from "../../../apis/boardApis";

// 인터페이스를 정의합니다.

interface BoardTop3Props {
  onDetailClick: (boardId: number) => void;
}

export default function BoardTop3({ onDetailClick }: BoardTop3Props) {
  // authView : true - signUp / false - signIn
  const [boardData, setBoardData] = useState<Board[]>([]); // 인터페이스를 적용하여 배열의 요소를 정확히 타입화합니다.
  const [cookies, setCookies] = useCookies();

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
                width: "300px",
                height: "300px",
                margin: "10px",
              }}
              onClick={() => onDetailClick(board.boardNumber)}
            >
              <Box textAlign="center">
                <Typography variant="h6">{board.boardTitle}</Typography>
                <Box display="flex" alignItems="center">
                  <Box
                    width={32}
                    height={32}
                    borderRadius="50%"
                    overflow="hidden"
                    mr={0} // 이미지와 닉네임 사이의 간격을 설정합니다.
                  >
                    <img
                      src={`http://localhost:4000/api/images/${board.boardWriterProfile}`}
                      width="100%"
                      height="100%"
                    />
                  </Box>
                  <Box>
                    <Typography
                      variant="body1"
                      gutterBottom
                      marginTop={"20px"}
                      marginBottom="2px"
                    >
                      {board.boardWriterNickname}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {board.boardWriteDate}
                    </Typography>
                  </Box>
                </Box>
              </Box>
            </Button>
          ))}
        </Box>
      </div>
    </>
  );
}
