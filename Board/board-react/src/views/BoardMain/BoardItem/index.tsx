import React from "react";
import { Box, Button } from "@mui/material";
import { Board, BoardItemProps } from "../../../interfaces";

const BoardItem: React.FC<BoardItemProps> = ({ board }) => {
  const handleBoardClick = () => {
    console.log("게시물 클릭:", board.boardNumber);
    //onClick(board);
  };

  return (
    <div key={board.boardNumber}>
      <Button fullWidth variant="outlined" sx={{ my: 2 }} onClick={handleBoardClick}>
        <Box textAlign="center">
          <h3>{board.boardTitle}</h3>
          <p>{board.boardWriterNickname}</p>
        </Box>
      </Button>
    </div>
  );
};

export default BoardItem;
