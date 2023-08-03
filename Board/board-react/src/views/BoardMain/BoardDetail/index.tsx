import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { Board, Liky } from "../../../interfaces";
import {
  Box,
  Button,
  Card,
  CardContent,
  CardMedia,
  IconButton,
  TextField,
  Typography,
  colors,
} from "@mui/material";
import CommentMain from "../../Comment";
import { useUserStore } from "../../../stores";
import {
  BoardApi,
  BoardIncreaseApi,
  BoardDeleteApi,
  boardEditApi,
} from "../../../apis/boardApis";
import {
  LikyApi,
  LikyRegisterApi,
  deleteLikyApi,
  getLikyCountApi,
} from "../../../apis/likyApis";

import ThumbUpAltIcon from '@mui/icons-material/ThumbUpAlt';
import ThumbUpOffAltIcon from '@mui/icons-material/ThumbUpOffAlt';

interface BoardDetailProps {
  onMainClick: () => void;
  onEditClick: (boardId: number) => void;
  currentPage: string;
  boardNumber: number; // 게시물 ID를 받아오도록 추가
}

export default function BoardDetail({
  onMainClick,
  onEditClick,
  currentPage,
  boardNumber,
}: BoardDetailProps) {
  const [boardData, setBoardData] = useState<Board | undefined>(undefined);
  const [cookies, setCookies] = useCookies();
  const [liked, setLiked] = useState<boolean>(false);
  const [liky, setLiky] = useState<Liky[]>([]);
  const [imageURL, setImageURL] = useState<string | null>(null);
  const { user } = useUserStore();
  const token = cookies.token;
  const [refresh, setRefresh] = useState(1);
  const [isInitialMount, setIsInitialMount] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        if (isInitialMount) {
          await BoardIncreaseApi(token, boardNumber);
          setIsInitialMount(false); // 최초 마운트 이후에는 다시 실행되지 않도록 상태 변경
        }
        const response = await BoardApi(token, boardNumber);
        const data = response.data;
        setBoardData(data);
        setLiked(data.boardLikeCount)
        const likyResponse = await LikyApi(token, boardNumber);
        const likyData = likyResponse.data;
        setLiky(likyData);
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setBoardData(undefined);
        setLiky([]);
        setLiked(false);
      }
    }
    fetchData();
  }, [refresh]); // Run only once on component mount

  const handleRefresh = () => {
    setRefresh(refresh * -1); // refresh 값을 변경하여 컴포넌트를 새로고침
  };

  const isCurrentUserPost = boardData?.boardWriterEmail === user?.userEmail;

  const handleDeleteClick = async () => {
    try {
      const response = await BoardDeleteApi(token, boardNumber);
      console.log(response);
      if (response && response.result) {
        alert("게시물이 삭제되었습니다.");
        onMainClick();
      } else {
        alert("게시물이 삭제에 실패했습니다.");
      }
    } catch (error) {
      console.error("게시글 삭제 실패:", error);
    }
  };

  const handleLikeClick = async () => {
    // 사용자의 닉네임을 가져옵니다.
    const userNickname = user?.userNickname;
    
    // 좋아요가 눌렸는지 여부를 판단합니다.
    const isLiked = liky.some((like) => like.likeUserNickname === userNickname);
    
    if (!isLiked) {
      // 사용자가 좋아요를 누르지 않은 경우, 좋아요 등록 API를 호출합니다.
      const likeUserdata = {
        boardNumber,
        userEmail: user.userEmail,
        likeUserProfile: user.userProfile,
        likeUserNickname: user.userNickname,
      };
      try {
        const response = await LikyRegisterApi(likeUserdata, token, boardNumber);
        if (response) {
          setLiked(true); // 좋아요 상태를 업데이트합니다.
          handleRefresh();
        }
      } catch (error) {
        console.error("좋아요 가져오기 실패:", error);
        setLiked(false);
      }
    } else {
      // 사용자가 이미 좋아요를 누른 경우, 좋아요 취소 API를 호출합니다.
      try {
        const response = await deleteLikyApi(token, boardNumber,userNickname);
        if (response && response.result) {
          setLiked(false); // 좋아요 상태를 업데이트합니다.
          handleRefresh();
        } else {
          alert("좋아요 취소 실패");
        }
      } catch (error) {
        console.error("좋아요 취소 실패:", error);
      }
    }
  };
  const handleEditClick = () => {
    // boardData.boardNumber를 전달하여 게시글 수정 페이지로 이동
    onEditClick(boardNumber);
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

  const imageUrl = `http://localhost:4000/api/images/${boardData.boardNumber}.jpg`;
  const videoUrl = `http://localhost:4000/api/videos/${boardData.boardNumber}.mp4`;

  return (
    <>
    <Card>
      <Box display="flex" justifyContent="center" marginTop="70px">
        <Box sx={{ maxWidth: 900, width: "100%" }}>
          <Card>
            <CardContent>
              <Box textAlign="center">
                <Typography variant="h4" gutterBottom>
                  {boardTitle}
                </Typography>
              </Box>
              <Box display="flex" alignItems="center">
                <Box
                  width={32}
                  height={32}
                  borderRadius="50%"
                  overflow="hidden"
                  mr={1} // 이미지와 닉네임 사이의 간격을 설정합니다.
                >
                  <img
                    src={`http://localhost:4000/api/images/${boardWriterProfile}`}
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
                    {boardWriterNickname}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {boardWriteDate}
                  </Typography>
                </Box>
              </Box>
              <Box textAlign="center">
                <Typography
                  variant="body2"
                  color="text.secondary"
                  gutterBottom
                  marginTop={"20px"}
                  sx={{ fontSize: "1.2rem", lineHeight: "1.8rem" }}
                >
                  {boardContent}
                </Typography>
              </Box>
              <Box my={2}>
                {/* 게시물 이미지를 보여줄 경우 */}
                {boardImage && (
                  <CardMedia
                    component="img"
                    height="auto"
                    image={imageUrl}
                    alt="게시물 이미지"
                    sx={{
                      display: "block", // Center align the image
                      margin: "0 auto", // Center align the image
                      maxWidth: "60%",
                      height: "auto",
                    }}
                  />
                )}
                {/* 게시물 동영상을 보여줄 경우 */}
                {boardVideo && (
                  <video
                    width="60%"
                    controls
                    style={{
                      display: "block", // Center align the video
                      margin: "0 auto", // Center align the video
                    }}
                  >
                    <source src={videoUrl} type="video/mp4" />
                    Your browser does not support the video tag.
                  </video>
                )}
                {/* 게시물 파일을 다운로드 링크로 보여줄 경우 */}
                {boardFile && (
                  <div style={{ textAlign: "center" }}>
                    <a
                      href={boardFile}
                      download
                      style={{
                        display: "block", // Center align the link
                        margin: "0 auto", // Center align the link
                      }}
                    >
                      게시물 파일 다운로드
                    </a>
                  </div>
                )}
              </Box>
              <Typography variant="body2" gutterBottom>
                조회수: {boardClickCount} | 좋아요: {boardLikeCount} | 댓글 수:{" "}
                {boardCommentCount}
              </Typography>
              <Box
                display="flex"
                justifyContent="space-between"
                alignItems="center"
              >
                {liked? (
                  // 사용자가 좋아요를 눌렀을 경우, 좋아요 취소 버튼 표시
                  <IconButton
                    color="primary"
                    onClick={handleLikeClick}
                  >
                    <ThumbUpAltIcon/>
                  </IconButton>
                ) : (
                  // 사용자가 좋아요를 누르지 않았을 경우, 좋아요 버튼 표시
                  <IconButton
                  color="primary"
                    onClick={handleLikeClick}
                  >
                    <ThumbUpOffAltIcon/>
                    </IconButton>
                )}
                {isCurrentUserPost && (
                  <>
                    <Box display="flex" alignItems="center">
                      <Typography
                        variant="body2"
                        color="primary"
                        sx={{
                          cursor: "pointer",
                          color: "black",
                          marginRight: "20px",
                          "&:hover": {
                            textDecoration: "underline", // Add underline effect on hover
                          },
                        }}
                        onClick={handleEditClick}
                      >
                        게시물 수정
                      </Typography>
                      <Typography
                        variant="body2"
                        color="primary"
                        sx={{
                          cursor: "pointer",
                          color: "black",
                          "&:hover": {
                            textDecoration: "underline", // Add underline effect on hover
                          },
                        }}
                        onClick={handleDeleteClick}
                      >
                        게시물 삭제
                      </Typography>
                    </Box>
                  </>
                )}
              </Box>
            </CardContent>
          </Card>
        </Box>
        
      </Box>
      <CommentMain boardNumber={boardNumber} />
      <Box
        display="flex"
        justifyContent="flex-end"
        sx={{
          maxWidth: 900,
          width: "100%",
          margin: "10px auto", 
        }}
      >
        <Button
          variant="contained"
          color="inherit"
          sx={{ backgroundColor: "#ffffff", color: "#000000" }}
          onClick={onMainClick}
        >
          이전
        </Button>
      </Box>
      </Card>
    </>
  );
}
