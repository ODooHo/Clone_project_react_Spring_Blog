import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import PersonIcon from "@mui/icons-material/Person";
import { useUserStore } from "../../stores";
import { useCookies } from "react-cookie";


interface NavigationProps {
  onMyPageClick: () => void;
  onHomeClick: () => void;
  currentPage: string;
}

export default function Navigation({onMyPageClick, onHomeClick, currentPage}: NavigationProps) {
  const { user, removeUser } = useUserStore();
  const [cookies,setCookies] = useCookies();

  const logOutHandler = () => {
    setCookies('token',"");
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
              <IconButton color="inherit" onClick={() => logOutHandler()}>
                <PersonIcon />
              </IconButton>
              <Button
            color="inherit"
            onClick={() => (currentPage === 'boardMain' ? onMyPageClick() : onHomeClick())} // 버튼 클릭에 따라 해당 함수 호출
            sx={{ color: 'white', backgroundColor: 'black', mr: 2 }}
          >
            {currentPage === 'boardMain' ? '마이페이지' : '홈으로'} {/* 버튼 텍스트 조건부 렌더링 */}
          </Button>
            </>
          ) : (
            <Button
              color="inherit"
              sx={{ color: "white", backgroundColor: "black", mr: 2 }}
            >
              Login
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
