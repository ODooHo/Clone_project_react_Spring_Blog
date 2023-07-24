export const tmp = '';
export interface Board {
    boardNumber: number;
    boardTitle: string;
    boardContent: string;
    boardImage: string;
    boardVideo: string;
    boardFile: string;
    boardWriterEmail: string;
    boardWriterProfile: string;
    boardWriterNickname: string;
    boardWriteDate: string;
    boardClickCount: number;
    boardLikeCount: number;
    boardCommentCount: number;
  }

  export interface BoardItemProps {
    board: Board;
    //onClick: (board: Board) => void; 
  }