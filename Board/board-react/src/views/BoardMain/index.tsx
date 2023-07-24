import { useState } from "react";
import { Box } from "@mui/material";
import BoardTop3 from "./BoardTop3";
import BoardList from "./BoardList";

export default function BoardMain() {
  // authView : true - signUp / false - signIn
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
