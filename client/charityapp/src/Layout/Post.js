import { useEffect } from 'react';
import { useState } from 'react';
import { Header, Comment, Feed, Modal, Button, Form, Pagination, Label, Checkbox, ModalContent } from 'semantic-ui-react';
import { AuthAPI, endpoints } from '../API';
import moment from "moment"
import cookies from 'react-cookies'
import './Post.css'

export default function Post({ user } = null) {
  let [posts, setPost] = useState([])
  let [like, setLike] = useState(posts.likes)
  let [isLiked, setIsLiked] = useState(false)
  const [count, setCount] = useState(0)
  let [winnerId, setWinnerId] = useState({
    winnerId: null
  })
  let [isChecked, setIsChecked] = useState(false)
  let [cost, setCost] = useState({
    cost: null
  })
  let [comment, setComment] = useState({
    content: "",
  });
  let [postID, setPostID] = useState({
    postId: null
  })
  let [isComment, setIsComment] = useState(true)
  const likeHandler = () => {
    setLike(isLiked ? like - 1 : like + 1)
    setIsLiked(!isLiked)
  }
  const [page, setPage] = useState(1)
  const handlePaginationChange = (e, { activePage }) => setPage(activePage);

  // hàm gọi API lấy bài viết
  const getPost = async () => {
    console.log(comment)
    AuthAPI.get(`${endpoints['post']}?page=${page}`, {
      headers: {
        'Authorization': `Bearer ${cookies.load('access_token')}`
      }

    }).then(res => {
      console.log(res.data.listPost)
      setPost(res.data.listPost)
      setCount(res.data.count)
    });

  }
  // hàm thay đổi giá trị của content
  function handleChange(event) {
    setComment({
      ...comment,
      [event.target.name]: event.target.value
    })
  };

  // hàm gọi API cho comment
  const handleComment = async (event) => {
    event.preventDefault();
    try {
      let res = await AuthAPI.post(endpoints['comments'](postID.postId), {

        "content": comment.content
      }, {
        headers: {
          'Authorization': `Bearer ${cookies.load('access_token')}`
        }
      })
      setComment(res.data)
      setIsComment(false)
    } catch (ex) {
      console.error(ex)
    }
  }

  // hàm gọi API ghi nhận thông tin người thắng đấu giá
  const handleChooseWinner = async (event) => {
    event.preventDefault();

    try {

      await AuthAPI.post(endpoints['winner'](postID.postId, winnerId.winnerId, cost.cost),{
        "cost":cost.cost,
        "winner-id":winnerId.winnerId
      } ,{
        headers: {
          'Authorization': `Bearer ${cookies.load('access_token')}`
        }
      })
    } catch (ex) {
      console.error(ex)
    }

  }

  useEffect(() => {
    document.title = "Home";
    getPost();
  }, [page])

  let items = []
  for (let i = 0; i < Math.ceil(count / 3); i++)
    items.push(
      <Pagination.Item>
        i+1
      </Pagination.Item>
    )

  return (
    <>
      {user == null ? (

        <Feed>
          {posts.map(post => {
            var timeAgo = moment(post.createdDate).fromNow()

            return (
              <>
                <span>{post.pos}</span>
                <div className="post">
                  <div className="postWrapper">
                    <div className="postTop">
                      <div className="postTopLeft">
                        <img className="postProfileImg" src={post.ownerPost.avatar} alt="avatar" />
                        <span className="postUsername">
                          {post.ownerPost.username}
                          <span> created a post</span>
                        </span>
                        <span className="postDate">{timeAgo}</span>
                      </div>
                      <div className="postTopRight">
                      </div>
                    </div>
                    <div className="postCenter">
                      <span className="postText">{post.content}</span>
                      <img className="postImg" src={post.image} alt="" />
                    </div>
                    <div className="postBottom">
                      <div className="postBottomLeft">
                        <img className="likeIcon" src="/assert/like.png" onClick={likeHandler} alt="" />
                        <img className="likeIcon" src="/assert/heart.png" onClick={likeHandler} alt="" />
                        {post.likes.length === 0 ? (
                          <span className="postLikeCounter">{post.likes} </span>
                        ) : (
                          <span className="postLikeCounter">{post.likes.length} </span>
                        )}
                      </div>
                      <div className="postBottomRight">
                        <>

                          <Modal
                            id="cmtModal"
                            trigger={<span>Comments</span>}>
                            <Modal.Content>

                            </Modal.Content>
                            <Modal.Content>
                              <Form onSubmit={handleComment} >
                                <Form.TextArea
                                  placeholder="Write a public comment..."
                                  name="content"
                                  value={comment.content}
                                  onChange={handleChange} />
                                <Button
                                  size='mini'
                                  content='Add comment'
                                  name="postId"
                                  value={post.postId}
                                  onClick={(e) => setPostID({

                                    postId: e.target.value
                                  })}
                                  labelPosition='left'
                                  icon='edit'
                                  primary />
                              </Form>
                            </Modal.Content>
                          </Modal>

                        </>
                      </div>
                    </div>
                  </div>
                </div>
              </>
            )
          }


          )}
        </Feed>
      ) : (
        <Feed>

          {posts.filter((p) => user.username === p.ownerPost.username)
            .map(post => {
              var timeAgo = moment(post.createdDate).fromNow()
              // let size = []
              // for (let i = 0; i < Math.ceil(posts.length / 3); i++)
              //   items.push(
              //     <Pagination.Item>
              //       i+1
              //     </Pagination.Item>
              //   )
              return (
                <>
                  <div className="post">
                    <div className="postWrapper">
                      <div className="postTop">
                        <div className="postTopLeft">
                          <img className="postProfileImg" src={post.ownerPost.avatar} alt="avatar" />
                          <span className="postUsername">
                            {post.ownerPost.username}
                            <span> created a post</span>
                          </span>
                          <span className="postDate">{timeAgo}</span>
                        </div>
                        <div className="postTopRight">
                        </div>
                      </div>
                      <div className="postCenter">
                        <span className="postText">{post.content}</span>
                        <img className="postImg" src={post.image} alt="" />
                      </div>
                      <div className="postBottom">
                        <div className="postBottomLeft">
                          <img className="likeIcon" src="/assert/like.png" onClick={likeHandler} alt="" />
                          <img className="likeIcon" src="/assert/heart.png" onClick={likeHandler} alt="" />
                          {post.likes.length === 0 ? (
                            <span className="postLikeCounter">{post.likes} </span>
                          ) : (
                            <span className="postLikeCounter">{post.likes.length} </span>
                          )}
                        </div>
                        <div className="postBottomRight">

                          <Modal
                            id="cmtModal"
                            trigger={<span>{post.comments.length} Comments</span>}
                            >
                            <Modal.Content>
                              <Header as='h3' dividing>
                                Comments
                              </Header>
                            </Modal.Content>

                            {post.comments.map(comment => {
                              var timeComment = moment(comment.createdDate).calendar()
                              return (
                                <>
                                  <Modal.Content>
                                    <Comment.Group minimal>
                                      <Comment>
                                        <Comment.Avatar as='a' src={comment.user.avatar} />
                                        <Comment.Content>
                                          <Comment.Author as='a'>{comment.user.username}</Comment.Author>
                                          <Comment.Metadata>
                                            <span>{timeComment}</span>
                                          </Comment.Metadata>
                                          <Comment.Text>{comment.content}</Comment.Text>
                                          <Comment.Actions>
                                            <Checkbox
                                              name={comment.content}
                                              value={comment.user.userId}
                                              onChange={(e) => {
                                                setWinnerId({
                                                  winnerId:comment.user.userId
                                                });
                                                setIsChecked(true)
                                                setPostID({
                                                  postId: post.postId
                                                })
                                                
                                                setCost({
                                                  cost:comment.content
                                                })
                                              }}
                                            />
                                          </Comment.Actions>
                                        </Comment.Content>
                                      </Comment>
                                    </Comment.Group>
                                  </Modal.Content>
                                </>
                              )
                            })}
                            <ModalContent></ModalContent>
                            <Modal.Actions>
                              {isChecked ? (
                                <Button
                                  secondary
                                  content="Choose winner"
                                  onClick={handleChooseWinner}
                                />
                              ) : (
                                <Button
                                  secondary
                                  content="Choose winner"
                                  disabled />
                              )}

                            </Modal.Actions>

                          </Modal>
                        </div>
                      </div>
                    </div>
                  </div>
                  {/* <Pagination
                    defaultActivePage={1}
                    firstItem={null}
                    lastItem={null}
                    pointing
                    secondary
                    totalPages={size.length}
                    activePage={page}
                    onPageChange={handlePaginationChange}
                  /> */}
                </>
              )

            }
            )}
        </Feed>
      )}

      <Pagination
        defaultActivePage={1}
        firstItem={null}
        lastItem={null}
        pointing
        secondary
        totalPages={items.length}
        activePage={page}
        onPageChange={handlePaginationChange}
      />
    </>
  );
}