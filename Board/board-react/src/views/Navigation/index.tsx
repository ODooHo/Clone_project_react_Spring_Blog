import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import { useUserStore } from "../../stores";
import { useCookies } from "react-cookie";
import SearchIcon from "@mui/icons-material/Search";
import HomeIcon from '@mui/icons-material/Home';

interface NavigationProps {
  onMyPageClick: () => void;
  onHomeClick: () => void;
  onSearchClick: () => void;
  currentPage: string;
}

export default function Navigation({
  onMyPageClick,
  onHomeClick,
  onSearchClick,
  currentPage,
}: NavigationProps) {
  const { user, removeUser } = useUserStore();
  const [cookies, setCookies] = useCookies();

  const logOutHandler = () => {
    setCookies("token", "");
    removeUser();
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar
        position="fixed"
        variant="outlined"
        sx={{ backgroundColor: "white", color: "black", boxShadow: "none" }}
      >
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Blog
          </Typography>
          {user ? (
            <>
              <IconButton
                color="inherit"
                onClick={() => currentPage === "search" ? onHomeClick() : onSearchClick()} // 검색 아이콘 클릭에 따라 페이지 이동
              >
                {currentPage === "search" ? <></> : <SearchIcon />} 
              </IconButton>
              <Button
                href="#text-buttons"
                color="inherit"
                onClick={() => logOutHandler()}>
                  로그아웃
              </Button>
              <Button
                href="#text-buttons"
                color="inherit"
                onClick={() =>
                  currentPage === "boardMain" ? onMyPageClick() : onHomeClick()
                } // 버튼 클릭에 따라 해당 함수 호출
              >
                {currentPage === "boardMain" ? "마이페이지" : "홈으로"}{" "}
              </Button>
            </>
          ) : (
            <>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
