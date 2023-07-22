import { useEffect, useState } from "react";
import { Box, Button, Card, Divider, Grid, Typography } from "@mui/material";
import axios from "axios";
import { useCookies } from "react-cookie";
import { useUserStore } from "../../stores";
import { MyPageApi } from "../../apis";

// 인터페이스를 정의합니다.
interface Board {
  boardNumber: number;
  boardTitle: string;
  boardContent: string;
  boardImage: string;
  boardVideo: string;
  boardFile: string;
  boardWriterEmail: string;
  boardWriterProfile: string;
  boardWriterNickname: string;
  boardWriteDate: string;
  boardClickCount: number;
  boardLikeCount: number;
  boardCommentCount: number;
}

interface BoardItemProps {
  board: Board;
  //onClick: () => void;
}

const BoardItem: React.FC<BoardItemProps> = ({ board }) => {
  return (
    <div key={board.boardNumber}>
      <Button
        fullWidth
        variant="outlined" // 배경색은 투명하고 테두리만 보이도록 변경
        sx={{ my: 2 }} // 글 간격을 생성합니다.
      >
        <Box textAlign="center">
          <h3>{board.boardTitle}</h3>
          <p>{board.boardWriterNickname}</p>
        </Box>
      </Button>
    </div>
  );
};

export default function MyPage() {
  // authView : true - signUp / false - signIn
  const [boardData, setBoardData] = useState<Board[]>([]); // 인터페이스를 적용하여 배열의 요소를 정확히 타입화합니다.
  const [userNickname,setUserNickname] = useState<string>('');


  const [cookies, setCookies] = useCookies();
  const { user, setUser } = useUserStore();

  useEffect(() => {
    async function fetchData() {
      try {
        const token = cookies.token;
        const response = await MyPageApi(token);
        const Nickname = response.data.userNickname;
        const data = response.data.userBoard;
        if (data) {
          setBoardData(data);
          setUserNickname(Nickname);
        } else {
          setBoardData([]);
          setUserNickname('');
        }
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setBoardData([]);
      }
    }
    fetchData();
  }, []); // Run only once on component mount

  const handleBoardClick = (board: Board) => {
    // 여기서 게시물을 눌렀을 때 동작을 추가하면 됩니다.
    console.log("게시물 클릭:", board.boardNumber);
  };

  return (
    <>
    <Box>
        <Box marginTop={"100px"}>
            <Typography variant="h5">{userNickname} 님 안녕하세요!</Typography>
        </Box>

    <Box>
      <Card
        sx={{ minWidth: 275, maxWidth: "50vw", padding: 5, marginTop: "100px" }}
      >
        <Box>
          <Typography variant="h5">내 게시물</Typography>
        </Box>
        <Box height={"50vh"} display="flex" flexDirection="column">
          <Box flex="1" overflow="auto">
            {boardData.map((board) => (
              <BoardItem key={board.boardNumber} board={board} />
            ))}
          </Box>
        </Box>
      </Card>
      </Box>
    </Box>
    </>
  );
}
