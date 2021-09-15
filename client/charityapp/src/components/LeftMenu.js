import { useState } from 'react';
import { useStore } from 'react-redux';
import { Sidebar, Menu, Header, Image, MenuItem, Button, Icon } from 'semantic-ui-react'
import '../components/LeftMenu.css'
import getUser from '../getUser';

const items = [
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yx/r/-XF4FQcre_i.png",
    name: "Friends"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yr/r/2uPlV4oORjU.png",
    name: "Saved"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/y7/r/AYj2837MmgX.png",
    name: "Memories"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yD/r/mk4dH3FK0jT.png",
    name: "Groups"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yA/r/A2tHTy6ibgT.png",
    name: "Charity"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yn/r/XEWvxf1LQAG.png",
    name: "Play Game"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yq/r/YF1bztyGuX-.png",
    name: "Messenger"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/y1/r/l6e-P1BHJLy.png",
    name: "Feed"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yv/r/QAyfjudAqqG.png",
    name: "Events"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yZ/r/i7hepQ2OeZg.png",
    name: "Pages"
  },
  {
    image: "https://static.xx.fbcdn.net/rsrc.php/v3/yP/r/Zy9sJGThmCS.png",
    name: "Favorites"
  }
];

export default function LeftMenu(props) {

  const [showMore, setShowMore] = useState(false);
  const numberOfItems = showMore ? items.length : 6;
  const store = useStore()

  let { user } = getUser(store)

  return (
    <>

      <Sidebar
        as={Menu}
        animation='overlay'
        icon='labeled'
        inverted
        vertical
        visible
        width='thin'
      >
        <Menu.Item as='a'>
          <Header as='h4' textAlign="left" >
            <Image id="ava" circular src={user.avatar} avatar size="mini" alt="avatar" />
            <span>{user.username}</span>
          </Header>
        </Menu.Item>
        {items.slice(0, numberOfItems).map((item, i) => (
          <MenuItem as="a">
            <Header as='h4' textAlign="left">
              <Image circular src={item.image} size="mini" /> {item.name}
            </Header>
          </MenuItem>
        )
        )}
        <MenuItem as="a">
          <Header as="h4" textAlign="left" onClick={() => setShowMore(!showMore)}>
            {showMore ? <Icon name="chevron circle up " color="grey" size="mini"
            /> : <Icon name="chevron circle down " color="grey" size="mini"
            />} See {showMore ? "less" : "more"}
          </Header>
        </MenuItem>
      </Sidebar>
    </>
  )
}