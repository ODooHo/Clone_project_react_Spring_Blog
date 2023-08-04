import { useEffect, useState } from "react";
import { Box, Button, Divider, Typography } from "@mui/material";
import { useCookies } from "react-cookie";
import { useUserStore } from "../../../stores";
import { Board } from "../../../interfaces";
import { BoardTop3Api } from "../../../apis/boardApis";
import { getImageApi } from "../../../apis/fileApis";

// 인터페이스를 정의합니다.

interface BoardTop3Props {
  onDetailClick: (boardId: number) => void;
}

export default function BoardTop3({ onDetailClick }: BoardTop3Props) {
  // authView : true - signUp / false - signIn
  const [boardData, setBoardData] = useState<Board[]>([]); // 인터페이스를 적용하여 배열의 요소를 정확히 타입화합니다.
  const [cookies, setCookies] = useCookies();
  const [profileImages, setProfileImages] = useState<{ [key: number]: string | null }>({});

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
  }, [cookies.token]); // Run only when cookies.token changes

  useEffect(() => {
    async function fetchImages() {
      try {
        const token = cookies.token;

        // Fetch profile images for all boards
        const imagePromises = boardData.map(async (board) => {
          const imageUrl = await getImageApi(token, board.boardWriterEmail);
          return { [board.boardNumber]: imageUrl };
        });

        // Wait for all image promises to resolve
        const imageResults = await Promise.all(imagePromises);

        // Combine all image URLs into a single object
        const images = imageResults.reduce((acc, image) => {
          return { ...acc, ...image };
        }, {});

        setProfileImages(images);
      } catch (error) {
        console.error("Error fetching profile images:", error);
      }
    }

    fetchImages();
  }, [boardData, cookies.token]);


  return (
    <>
 <Box
      maxHeight="100vh"
      padding="5px"
      marginTop="100px"
      display="flex"
      flexDirection="column"
      alignItems="center"
    >
      <Typography variant="h5" align="center" gutterBottom>
        주간 Top3 게시글
      </Typography>
      <Divider />
      <Box
        display="flex"
        flexDirection="row"
        justifyContent="center"
        alignItems="flex-start" // Adjust alignment to the top
        flexWrap="wrap"
      >
        {boardData.map((board) => (
          <Button
            key={board.boardNumber}
            variant="outlined"
            color="inherit"
            sx={{
              width: "300px",
              height: "300px",
              margin: "10px",
              display: "flex", // Add display flex to make flexbox work for the Button
              flexDirection: "column", // Stack the elements vertically
              justifyContent: "center", // Center children vertically
            }}
            onClick={() => onDetailClick(board.boardNumber)}
          >
            <Typography
              variant="h6"
              sx={{
                maxWidth: "100%", // Allow the title to wrap to a new line
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
                textAlign: "center",
              }}
            >
              {board.boardTitle}
            </Typography>
            <Box
              display="flex"
              alignItems="center"
              sx={{
                marginTop: "8px", // Reduce the space between the title and profile info
                textAlign: "center",
              }}
            >
              <Box
                width={32}
                height={32}
                borderRadius="50%"
                overflow="hidden"
                mr={0}
              >
                <img
                  src={profileImages[board.boardNumber] || "default-image-url.jpg"}
                  width="100%"
                  height="100%"
                />
              </Box>
              <Box marginLeft="8px">
                <Typography
                  variant="body1"
                  gutterBottom
                  marginTop="2px"
                  marginBottom="2px"
                >
                  {board.boardWriterNickname}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {board.boardWriteDate}
                </Typography>
              </Box>
            </Box>
          </Button>
        ))}
      </Box>
    </Box>
    </>
  );
}
