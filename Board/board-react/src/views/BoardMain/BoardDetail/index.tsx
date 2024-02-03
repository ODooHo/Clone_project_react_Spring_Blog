import { useEffect, useState } from "react";
import { Board, Liky } from "../../../interfaces";
import {
  Box,
  Card,
  CardContent,
  CardMedia,
  IconButton,
  Typography,
} from "@mui/material";
import CommentMain from "../../Comment";
import { useUserStore } from "../../../stores";
import {
  BoardApi,
  BoardIncreaseApi,
  BoardDeleteApi,
} from "../../../apis/boardApis";
import { likyControlApi } from "../../../apis/likyApis";
import ThumbUpAltIcon from "@mui/icons-material/ThumbUpAlt";
import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
import { fileDownloadApi, getVideoApi } from "../../../apis/fileApis";
import DownloadIcon from "@mui/icons-material/Download";
import NavigateBeforeIcon from "@mui/icons-material/NavigateBefore";

interface BoardDetailProps {
  onMainClick: () => void;
  onEditClick: (boardId: number) => void;
  currentPage: string;
  boardId: number; // 게시물 ID를 받아오도록 추가
}

export default function BoardDetail({
  onMainClick,
  onEditClick,
  currentPage,
  boardId,
}: BoardDetailProps) {
  const [boardData, setBoardData] = useState<Board | undefined>(undefined);
  const [liked, setLiked] = useState<boolean>(false);
  const { user } = useUserStore();
  const token = localStorage.getItem("token");
  const refreshToken = localStorage.getItem("refreshToken");
  const [refresh, setRefresh] = useState(1);
  const [isInitialMount, setIsInitialMount] = useState(true);
  const [videoUrl, setVideoUrl] = useState<string | undefined>(undefined);


  useEffect(() => {
    async function fetchData() {
      try {
        if (isInitialMount) {
          await BoardIncreaseApi(token, refreshToken, boardId);
          setIsInitialMount(false); // 최초 마운트 이후에는 다시 실행되지 않도록 상태 변경
        }
        const [boardResponse] = await Promise.all([
          BoardApi(token, refreshToken, boardId),
        ]);
        const data = boardResponse.data;
        setBoardData(data);
        setLiked(data.likesCount);
        const likyData = data.likes;
        const userLiked = likyData.some(
          (like: Liky) => like.userEmail === user?.userEmail
        );
        setLiked(userLiked);
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setBoardData(undefined);
        setLiked(false);
      }
    }
    fetchData();
  }, [refresh]); // Run only once on component mount


  useEffect(() => {
    async function fetchMedia() {
      try {
        if (!boardData || boardData.video === null) return; // Return early if boardData is not available yet
        const videoUrl = await getVideoApi(boardData.video)
        console.log(videoUrl)
        setVideoUrl(videoUrl || undefined);
      } catch (error) {
        console.error("게시글 가져오기 실패:", error);
        setVideoUrl(undefined);
      }
    }
    fetchMedia();
  }, [boardData?.content, boardData?.commentsCount, token]);

  const handleRefresh = () => {
    setRefresh(refresh * -1); // refresh 값을 변경하여 컴포넌트를 새로고침
  };

  const isCurrentUserPost = boardData?.user.userEmail === user?.userEmail;

  const handleDeleteClick = async () => {
    try {
      const response = await BoardDeleteApi(token, refreshToken, boardId);
      if (response) {
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
    try {
      await likyControlApi(token, refreshToken, boardId);
      handleRefresh();
    } catch (error) {
      console.error("좋아요 처리 실패:", error);
    }
  };

  const handleDownloadClick = async (fileName: string) => {
    try {
      const response = await fileDownloadApi(token, refreshToken, fileName);
  
      if (response.headers && response.headers['content-type']) {
        // 파일 다운로드를 위한 Blob 생성
        const blob = new Blob([response.data], { type: response.headers['content-type'] });
  
        // Blob을 URL로 변환
        const fileUrl = URL.createObjectURL(blob);
  
        // 다운로드 링크 생성
        const link = document.createElement("a");
        link.href = fileUrl;
        link.download = fileName;
  
        // 링크를 body에 추가하고 클릭
        document.body.appendChild(link);
        link.click();
  
        // 링크와 Blob URL 제거
        document.body.removeChild(link);
        URL.revokeObjectURL(fileUrl);
      } else {
        console.error("File download failed: Content type not found in response headers");
      }
    } catch (error) {
      console.error("File download failed:", error);
    }
  };
  const handleEditClick = () => {
    // boardData.boardId를 전달하여 게시글 수정 페이지로 이동
    onEditClick(boardId);
  };

  if (!boardData) {
    return <div>로딩 중...</div>;
  }

  const {
    title: boardTitle,
    content: boardContent,
    image: boardImage,
    video: boardVideo,
    file: boardFile,
    boardWriteDate,
    clickCount: boardClickCount,
    likesCount: boardLikeCount,
    commentsCount: boardCommentCount,
  } = boardData;

  const defaultImage = "default-image.png";

  const contentLines = boardContent.split("\n").map((line, index) => (
    <Typography
      variant="body2"
      key={index}
      gutterBottom
      marginTop="20px"
      marginBottom="10px" // 줄바꿈 사이의 간격 조절
      sx={{ fontSize: "1.2rem", lineHeight: "1.8rem" }}
    >
      {line}
    </Typography>
  ));


  return (
    <>
      <Card elevation={0} sx={{ backgroundColor: "#FAFAFA" }}>
        <Box display="flex" justifyContent="center" marginTop="70px">
          <Box sx={{ maxWidth: 900, width: "100%" }}>
            <Card>
              <IconButton
                color="inherit"
                sx={{ backgroundColor: "#ffffff", color: "#000000" }}
                onClick={onMainClick}
              >
                <NavigateBeforeIcon sx={{ fontSize: "2rem" }} />
              </IconButton>
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
                      src={boardData.user.userProfile || defaultImage}
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
                      {boardData.user.userNickname}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {boardWriteDate}
                    </Typography>
                  </Box>
                </Box>
                <Box>{contentLines}</Box>
                <Box my={2}>
                  {/* 게시물 이미지를 보여줄 경우 */}
                  {boardImage && (
                    <CardMedia
                      component="img"
                      height="auto"
                      image={boardImage || undefined}
                      alt="게시물 이미지"
                      sx={{
                        display: "block", // Center align the image
                        margin: "0 auto", // Center align the image
                        maxWidth: "60%",
                        height: "auto",
                        marginBottom: "20px",
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
                      src={videoUrl || undefined}
                    ></video>
                  )}
                  {/* 게시물 파일을 다운로드 링크로 보여줄 경우 */}
                  {boardFile && (
                    <IconButton
                      color="secondary"
                      sx={{
                        cursor: "pointer",
                        "&:hover": {
                          textDecoration: "underline", // Add underline effect on hover
                        },
                      }}
                      onClick={() => 
                        handleDownloadClick(boardFile)}
                    >
                      <DownloadIcon />
                    </IconButton>
                  )}
                </Box>
                <Typography variant="body2" gutterBottom>
                  조회수: {boardClickCount} | 좋아요: {boardLikeCount} | 댓글:{" "}
                  {boardCommentCount}
                </Typography>
                <Box
                  display="flex"
                  justifyContent="space-between"
                  alignItems="center"
                >
                  {liked ? (
                    // 사용자가 좋아요를 눌렀을 경우, 좋아요 취소 버튼 표시
                    <IconButton color="primary" onClick={handleLikeClick}>
                      <ThumbUpAltIcon />
                    </IconButton>
                  ) : (
                    // 사용자가 좋아요를 누르지 않았을 경우, 좋아요 버튼 표시
                    <IconButton color="primary" onClick={handleLikeClick}>
                      <ThumbUpOffAltIcon />
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
        <CommentMain boardId={boardId} />
        <Box
          display="flex"
          justifyContent="flex-end"
          sx={{
            maxWidth: 900,
            width: "100%",
            margin: "10px auto",
          }}
        ></Box>
      </Card>
    </>
  );
}
