import { useState } from "react";
import { Box } from "@mui/material";
import BoardTop3 from "./BoardTop3";
import BoardList from "./BoardList";

export default function Authentication() {
  // authView : true - signUp / false - signIn
  const [authView, setAuthView] = useState<boolean>(false);
  return (
    <>
      <Box>
        <Box>
        <BoardTop3/>
        </Box>
        <Box>
        <BoardList/>
        </Box>
      </Box>
    </>
  );
}
