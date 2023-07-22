import React, { useEffect, useState } from 'react'
import Navigation from '../../Navigation'
import { Box } from '@mui/material'
import Authentication from '../../Authentication'
import BoardMain from '../../BoardMain'
import { useUserStore } from '../../../stores'
import { useCookies } from 'react-cookie'
import axios from 'axios'
import MyPage from '../../MyPage'


export default function MainLayout() {
    const [cookies] = useCookies();
    const {user} = useUserStore();
    const [currentPage, setCurrentPage] = useState('boardMain'); // 초기 페이지를 'boardMain'으로 설정합니다

    const handleMyPageClick = () =>{
      setCurrentPage('myPage');
    }

    const handleHomeClick = () => {
      setCurrentPage('boardMain');
    }
    return (
    <>
 <Navigation
        onMyPageClick={handleMyPageClick}
        onHomeClick={handleHomeClick}
        currentPage={currentPage} // 현재 페이지 상태를 Navigation 컴포넌트에 전달
      />
      {user ? (
        currentPage === 'boardMain' ? (
          <BoardMain />
        ) : (
          <MyPage />
        )
      ) : (
        <Authentication />
      )}
    </>
  )
}
