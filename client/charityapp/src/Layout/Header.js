import { Menu, Container, Dropdown, Icon, Label, Feed, Search, MenuItem, Card } from 'semantic-ui-react'
import '../Layout/Header.css'
import cookies from 'react-cookies'
import { useStore } from "react-redux"
import getUser from '../getUser'

const languageOptions = [
    { key: 'Vietnamese', text: 'Vietnamese', value: 'Vietnamese' },
    { key: 'English', text: 'English', value: 'English' },

]

function Header() {
    const store = useStore()

    let { user } = getUser(store)

    if (cookies.load("user") != null)
        user = cookies.load("user")
    let r = <></>
    const imageProps = {
        avatar: true,
        spaced: 'right',
        src: user.avatar
    }
    if (user != null)
        r = <Label as='a' color='black' content={user.username} image={imageProps} />

    const logout = () => {
        cookies.remove("Token")
        window.location.href = "/"
    }

    return (
        <div>

            <Menu fixed='top' inverted  >
                <Container>
                    <Menu.Item as='a' header>
                        Charity App
                        <Search id="searchID" placeholder="Search Charity" />
                    </Menu.Item>
                    <Menu.Menu position="right">
                        <Menu.Item as='a'><Icon name='home' size="large" /></Menu.Item>
                        <Menu.Item as='a'><Icon name='like' size="large" /></Menu.Item>
                        <Menu.Item as='a'><Icon name='group' size="large" /></Menu.Item>
                        <Menu.Item as='a'><Icon name='gamepad' size="large" /></Menu.Item>
                    </Menu.Menu>
                    <Menu.Menu position='right'>
                        <Menu.Item >{r}</Menu.Item>
                        <Menu.Item icon="bell outline" onClick={CardExampleContentBlock} >
                        
                            {/* <Dropdown icon='bell outline' > */}
                            {/* <Dropdown.Menu className="menuNotification">
                                    <Dropdown.Header>Notifications</Dropdown.Header>
                                    <div className="itemNotification">
                                        <Dropdown.Item >
                                            <div className='notification'>
                                                <Feed>
                                                    <Feed.Event  >
                                                        <Feed.Label image='https://react.semantic-ui.com/images/avatar/small/elliot.jpg' />
                                                        <Feed.Content >
                                                            <Feed.Summary text>
                                                                You added <a>Jenny Hess</a> to your <a>coworker</a> group groupgroup group group aaaaâ.
                                                            </Feed.Summary>
                                                            <Feed.Date content='1 day ago' style={{ margin: '0.25em 0' }} />
                                                        </Feed.Content>
                                                    </Feed.Event>
                                                </Feed>
                                            </div>
                                        </Dropdown.Item>
                                        <Dropdown.Divider />
                                        <Dropdown.Item>
                                            <div className='notification'>
                                                <Feed>
                                                    <Feed.Event>
                                                        <Feed.Label image='https://react.semantic-ui.com/images/avatar/small/elliot.jpg' />
                                                        <Feed.Content >
                                                            <Feed.Summary text>
                                                                You added <a>Jenny Hess</a> to your <a>coworker</a> group groupgroup group group aaaaâ.
                                                            </Feed.Summary>
                                                            <Feed.Date content='1 day ago' style={{ margin: '0.25em 0' }} />
                                                        </Feed.Content>
                                                    </Feed.Event>
                                                </Feed>
                                            </div>
                                        </Dropdown.Item>
                                        <Dropdown.Divider />
                                        <Dropdown.Item>
                                            <div className='notification'>
                                                <Feed  >
                                                    <Feed.Event  >
                                                        <Feed.Label image='https://react.semantic-ui.com/images/avatar/small/elliot.jpg' />
                                                        <Feed.Content >
                                                            <Feed.Summary text>
                                                                You added <a>Jenny Hess</a> to your <a>coworker</a> group groupgroup group group aaaaâ.aaaaaaaaaaaaaaaaâ
                                                            </Feed.Summary>
                                                            <Feed.Date content='1 day ago' style={{ margin: '0.25em 0' }} />
                                                        </Feed.Content>

                                                    </Feed.Event>
                                                </Feed>
                                            </div>
                                        </Dropdown.Item>
                                        <Dropdown.Divider />
                                        <Dropdown.Item>
                                            <div className='notification'>
                                                <Feed  >
                                                    <Feed.Event  >
                                                        <Feed.Label image='https://react.semantic-ui.com/images/avatar/small/elliot.jpg' />
                                                        <Feed.Content >
                                                            <Feed.Summary text>
                                                                You added <a>Jenny Hess</a> to your <a>coworker</a> group groupgroup group group aaaaâ.
                                                            </Feed.Summary>
                                                            <Feed.Date content='1 day ago' style={{ margin: '0.25em 0' }} />
                                                        </Feed.Content>
                                                    </Feed.Event>
                                                </Feed>
                                            </div>
                                        </Dropdown.Item>
                                        <Dropdown.Divider />
                                    </div>
                                </Dropdown.Menu>
                            </Dropdown> */}
                        </Menu.Item>
                        <Menu.Item >
                            <Dropdown
                                icon='world'
                                text
                                className='icon'
                                options={languageOptions}
                                defaultValue={languageOptions[1].value}
                            />
                        </Menu.Item>
                        <Menu.Item onClick={logout} >
                            <Icon link='#' name='sign-out' style={{ marginLeft: '0.5em' }} />
                        </Menu.Item>

                    </Menu.Menu>

                </Container>
            </Menu>

        </div>
    );
}

const CardExampleContentBlock = () => (
    <Card>
      <Card.Content>
        <Card.Header>Recent Activity</Card.Header>
      </Card.Content>
      <Card.Content>
        <Feed>
          <Feed.Event>
            <Feed.Label image='/images/avatar/small/jenny.jpg' />
            <Feed.Content>
              <Feed.Date content='1 day ago' />
              <Feed.Summary>
                You added <a>Jenny Hess</a> to your <a>coworker</a> group.
              </Feed.Summary>
            </Feed.Content>
          </Feed.Event>
  
          <Feed.Event>
            <Feed.Label image='/images/avatar/small/molly.png' />
            <Feed.Content>
              <Feed.Date content='3 days ago' />
              <Feed.Summary>
                You added <a>Molly Malone</a> as a friend.
              </Feed.Summary>
            </Feed.Content>
          </Feed.Event>
  
          <Feed.Event>
            <Feed.Label image='/images/avatar/small/elliot.jpg' />
            <Feed.Content>
              <Feed.Date content='4 days ago' />
              <Feed.Summary>
                You added <a>Elliot Baker</a> to your <a>musicians</a> group.
              </Feed.Summary>
            </Feed.Content>
          </Feed.Event>
        </Feed>
      </Card.Content>
    </Card>
  )


export default Header;