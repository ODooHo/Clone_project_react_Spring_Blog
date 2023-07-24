import React, { useState } from 'react';
import Main from './Main';
import WriteBoard from './WriteBoard';
import PatchUser from './PatchUser';
import { useUserStore } from '../../stores';

export default function MyPage() {
  const {user} = useUserStore();
  const [currentPage, setCurrentPage] = useState('Main'); // 초기 페이지를 'boardMain'으로 설정합니다

  const handleWriteBoardClick = () => {
    setCurrentPage('WriteBoard')
  }
  const handlePatchUserClick = () => {
    setCurrentPage('PathUser')
  }
  const handleMainClick = () => {
    setCurrentPage('Main')
  }

  return (
    <>
        {currentPage === 'Main' ? (
          <Main 
          onWriteBoardClick={handleWriteBoardClick}
          onPatchUserClick={handlePatchUserClick}
          currentPage={currentPage}
          />
        ) : currentPage === 'WriteBoard' ? (
          <WriteBoard 
          onMainClick={handleMainClick}
          currentPage={currentPage}
          />
        ) :  (<PatchUser        
          onMainClick={handleMainClick}
          currentPage={currentPage} />)}
    </>
  );
}
