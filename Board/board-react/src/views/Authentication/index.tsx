import { useState } from "react";
import SignUp from "./SignUp";
import SignIn from "./SignIn";
import { Box } from "@mui/material";

export default function Authentication() {
  // authView : true - signUp / false - signIn
  const [authView, setAuthView] = useState<boolean>(false);

  const backgroundStyle = {
    backgroundImage: 'url("1.jpg")', // Replace with your image path
    backgroundSize: "cover",
    backgroundPosition: "center",
  };

  return (
    <>
      <Box display="flex" height="100vh">
        <Box
          flex={1}
          display="flex"
          justifyContent="center"
          alignItems="center"
          sx={backgroundStyle}
        ></Box>
           <Box
          flex={1}
          display="flex"
          justifyContent="center"
          alignItems="center"
        >
          {authView ? (<SignUp setAuthView = {setAuthView}/>) : (<SignIn setAuthView = {setAuthView}/>)}
        </Box>
      </Box>
    </>
  );
}
