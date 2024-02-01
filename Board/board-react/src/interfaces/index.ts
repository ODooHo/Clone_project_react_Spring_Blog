export interface Board {
    id: number;
    title: string;
    content: string;
    image: string;
    video: string;
    file: string;
    boardWriteDate: string;
    clickCount: number;
    likesCount: number;
    commentCount: number;
    user: User;
    comments:Comment;
    likes : Liky;
  }

  export interface BoardItemProps {
    board: Board;
    onClick: () => void; 
  }


  export interface Comment{
    id : number;
    commentWriteDate: string;
    commentContent: string;
    board : Board;
    user : User;
  }

  export interface Liky{
    id : number;
    board : Board;
    user : User;
  }

  export interface PopularSearchList{
    popularTerm : string;
    popularSearchCount : number;
  }


  export interface User{
    userEmail : string;
    userPassword : string;
    userNickname : string;
    userPhoneNumber : string;
    userAddress : string;
    userProfile : string;
  }