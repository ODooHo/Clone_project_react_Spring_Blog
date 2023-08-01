import { useEffect, useState } from "react";
import { Box, Button, Card, Divider, Grid, Typography } from "@mui/material";
import { Board } from "../../../interfaces";
import { useCookies } from "react-cookie";
import { MyPageApi } from "../../../apis/userApis";
// 인터페이스를 정의합니다.

interface MainProps {
  onWriteBoardClick: () => void;
  onPatchUserClick: () => void;
  onProfileClick: () => void;
  onDetailClick: (boardId: number) => void;
  currentPage: string;
}

export default function Main({
  onWriteBoardClick,
  onPatchUserClick,
  onProfileClick,
  onDetailClick,
  currentPage,
}: MainProps) {
  // authView : true - signUp / false - signIn
  const [boardData, setBoardData] = useState<Board[]>([]); // 인터페이스를 적용하여 배열의 요소를 정확히 타입화합니다.
  const [userNickname, setUserNickname] = useState<string>("");
  const [userProfile, setUserProfile] = useState<string>("");
  const [cookies, setCookies] = useCookies();

  useEffect(() => {
    async function fetchData() {
      try {
        const token = cookies.token;
        const response = await MyPageApi(token);
        const Nickname = response.data.userNickname;
        const Profile = response.data.userProfile;
        const data = response.data.userBoard;
        if (data) {
          setBoardData(data);
          setUserNickname(Nickname);
          setUserProfile(Profile);
        } else {
          setBoardData([]);
          setUserNickname("");
          setUserProfile("");
        }
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setBoardData([]);
      }
    }
    fetchData();
  }, []); // Run only once on component mount

  return (
    <>
      <Box display="flex" flexDirection="column" alignItems="center">
        <Box marginTop="100px" marginBottom="10px">
          <Box
            display="flex"
            alignItems="center"
            justifyContent="center"
            mt={2}
            marginBottom="20px"
          >
            <Box
              width={28}
              height={28}
              borderRadius="50%"
              overflow="hidden"
              mx={1} // 수정: 이미지 좌우 여백을 위해 mx를 사용합니다.
            >
              <img
                src={`http://localhost:4000/api/images/${userProfile}`}
                width="100%"
                height="100%"
              />
            </Box>
            <Typography variant="h5">{userNickname} 님 안녕하세요!</Typography>
          </Box>
          <Button
            variant="contained"
            color="primary"
            sx={{
              backgroundColor: "black",
            }}
            onClick={onPatchUserClick}
          >
            개인정보 수정
          </Button>
          <Button
            variant="contained"
            color="primary"
            sx={{
              backgroundColor: "black",
            }}
            onClick={onProfileClick}
          >
            프로필 사진 변경
          </Button>
        </Box>

        <Box width="100vw">
          <Card sx={{ height: "200vh", padding: 5, marginBottom: "10px" }}>
            <Box
              display="flex"
              justifyContent="space-between"
              alignItems="center"
              marginLeft="10px"
              marginRight="10px"
            >
              <Typography variant="h5" sx={{ flex: 1 }}>
                내 게시물
              </Typography>
              <Button
                variant="contained"
                color="primary"
                sx={{
                  backgroundColor: "black",
                  flex: "none",
                  width: "300px",
                  height: "50px",
                  marginRight: "10%",
                }}
                onClick={() => onWriteBoardClick()}
              >
                글쓰기
              </Button>
            </Box>

            <Box width="50vw" height="50vh" overflow="auto">
              {boardData.map((board) => (
                <div key={board.boardNumber}>
                  <Button
                    fullWidth
                    variant="outlined"
                    sx={{ my: 2 }}
                    onClick={() => onDetailClick(board.boardNumber)}
                  >
                    <Box textAlign="center">
                      <Typography variant="h6">{board.boardTitle}</Typography>
                      <Box
                        display="flex"
                        alignItems="center"
                        justifyContent="center"
                        mt={2}
                      >
                        <Box
                          width={28}
                          height={28}
                          borderRadius="50%"
                          overflow="hidden"
                          mx={1} // 수정: 이미지 좌우 여백을 위해 mx를 사용합니다.
                        >
                          <img
                            src={`http://localhost:4000/api/images/${board.boardWriterProfile}`}
                            width="100%"
                            height="100%"
                          />
                        </Box>
                        <Typography>{board.boardWriterNickname}</Typography>
                      </Box>
                    </Box>
                  </Button>
                </div>
              ))}
            </Box>
          </Card>
        </Box>
      </Box>
    </>
  );
}
