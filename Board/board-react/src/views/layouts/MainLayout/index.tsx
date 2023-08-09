import { useState } from "react";
import Navigation from "../../Navigation";
import Authentication from "../../Authentication";
import BoardMain from "../../BoardMain";
import { useUserStore } from "../../../stores";
import MyPage from "../../MyPage";
import Search from "../../Search";
import { ThemeProvider } from "@mui/material";
import theme from "../../../theme/theme";
export default function MainLayout() {
  const { user } = useUserStore();
  const [currentPage, setCurrentPage] = useState("boardMain"); // 초기 페이지를 'boardMain'으로 설정합니다

  const handleMyPageClick = () => {
    setCurrentPage("myPage");
  };

  const handleHomeClick = () => {
    setCurrentPage("boardMain");
  };

  const handleSearchClick = () => {
    setCurrentPage("search");
  };

  return (
    <>
    <ThemeProvider theme={theme}>
      <Navigation
        onMyPageClick={handleMyPageClick}
        onHomeClick={handleHomeClick}
        onSearchClick={handleSearchClick}
        currentPage={currentPage} // 현재 페이지 상태를 Navigation 컴포넌트에 전달
      />
      {user ? (
        currentPage === "boardMain" ? (
          <BoardMain />
        ) : currentPage === "myPage" ? (
          <MyPage />
        ) : (
          <Search />
        )
      ) : (
        <Authentication />
      )}
      </ThemeProvider>
    </>
  );
}
