import React from 'react';
import Header from './Header';
import Modal from './Modal/Modal';

const PublicRoute = ({children }) => {

  return (
    <>
      <Header></Header>
      <main>{children}</main>
      <Modal></Modal>
    </>
  );
};

export default PublicRoute;