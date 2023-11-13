import React from 'react'
import { styled } from 'styled-components'

const Nav = () => {
  return (
    <Wrap>
      <LinkBox>
        <LinkItem $src="link_01.gif" />
        <LinkItem $src="link_02.gif" />
        <LinkItem $src="link_03.gif" />
        <LinkItem $src="link_04.gif" />
        <LinkItem $src="link_05.gif" />
        <LinkItem $src="link_06.gif" />
        <LinkItem $src="link_07.gif" />
      </LinkBox>
    </Wrap>
  )
}

const Wrap = styled.div`
  width: 100%;
  height: 167px;
  background-color: #f5f5f9;
`

const LinkBox = styled.ul`
  width: 980px;
  height: 100%;
  margin: 0 auto;
  display: flex;
`

const LinkItem = styled.li`
  width: 100%;
  height: 100%;
  background-image: url(${props=>props.$src});
  background-repeat: no-repeat;
  background-position: center;
  cursor: pointer;
`


export default Nav