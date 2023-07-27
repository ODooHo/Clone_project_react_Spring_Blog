import React, { useState } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { Board } from '../../../interfaces';
import { useCookies } from 'react-cookie';
import { SearchBoardApi } from '../../../apis/searchApis';


interface SearchMainProps{
  onDetailClick : (boardId:number) => void;
  currentPage : string;
  boardNumber: number;
}

export default function SearchMain({
  onDetailClick,
  currentPage,
  boardNumber
}:SearchMainProps) {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState<Board[]>([]);
  const [cookies] = useCookies();

  const handleSearchChange = () => {
    //setSearchQuery(event.target.value);
  };

  const handleSearch = async () => {
    const token = cookies.token;
    const count = 1;
    const data =  {
      popularTerm : searchQuery
    }
    try{
      const response = await SearchBoardApi(data,token);
      const responseData = response.data;
      setSearchResults(responseData);
      if(!response){
        console.error("검색 실패!");
        setSearchResults([]);
      }
    }catch(error){
      console.error("검색 실패!",error);
      setSearchResults([]);
    }
  };

  return (
    <Box marginTop = "50px"
     sx={{ padding: 2 }}>
      <Card variant="outlined">
        <CardContent>
          <Typography variant="h5" gutterBottom>
            검색
          </Typography>
          <TextField
            fullWidth
            onChange={(e) => setSearchQuery(e.target.value)}
            label="검색어를 입력하세요"
            variant="outlined"
            sx={{ mb: 2 }}
          />
          <Button
            variant="contained"
            color="primary"
            onClick={handleSearch}
          >
            검색
          </Button>
          {searchResults.length > 0 ? (
          <Box flex="1" overflow="auto">
          {searchResults.map((board) => (
            <div key={board.boardNumber}>
            <Button
              fullWidth
              variant="outlined"
              sx={{ my: 2 }}
              onClick={() => onDetailClick(board.boardNumber)}
            >
              <Box textAlign="center">
                <h3>{board.boardTitle}</h3>
                <p>{board.boardWriterNickname}</p>
              </Box>
            </Button>
          </div>
          ))}
        </Box>
          ) : (
            <Typography variant="body1" sx={{ mt: 2 }}>
              검색 결과가 없습니다.
            </Typography>
          )}
        </CardContent>
      </Card>
    </Box>
  );
}
