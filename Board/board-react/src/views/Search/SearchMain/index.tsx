import React, { useState } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';

export default function SearchMain() {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState([]);

  const handleSearchChange = () => {
    //setSearchQuery(event.target.value);
  };

  const handleSearch = () => {
    // 여기서는 빈 배열로 대체하였지만 실제로는 API 호출을 통해 검색 결과를 받아와야 합니다.
    setSearchResults([]);
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
            value={searchQuery}
            onChange={handleSearchChange}
            placeholder="검색어를 입력하세요"
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
            <ul>
              {searchResults.map((result, index) => (
                <li key={index}>{result}</li>
              ))}
            </ul>
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
