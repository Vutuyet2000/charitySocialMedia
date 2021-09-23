import { useEffect } from 'react';
import { useState } from 'react';
import { useStore } from 'react-redux';
import { Label, Image, Feed, Modal, Pagination, Button, Comment, Header, Form, ModalContent, ModalDescription } from 'semantic-ui-react';
import API, { AuthAPI, endpoints } from '../API';
import moment from "moment"
import cookies from 'react-cookies'

import './Post.css'
export default function Post(props) {
  const [posts, setPost] = useState([])
  const [like, setLike] = useState(posts.likes)
  const [isLiked, setIsLiked] = useState(false)
  const [count, setCount] = useState(0)
  const [open, setOpen] = useState(true)
  const currentUser=props.username
  const likeHandler = () => {
    setLike(isLiked ? like - 1 : like + 1)
    setIsLiked(!isLiked)
  }
  const [page, setPage] = useState(1)

  function handlePageChange(activePage) {
    setPage(activePage)
  }
  const getPost = () => {
    // const paramsString = queryString.stringify(page)
    console.log(page)
    AuthAPI.get(endpoints['post'], {
      headers: {
        'Authorization': `Bearer ${cookies.load('access_token')}`
      }

    }).then(res => {
      console.log(res.data.listPost)
      setPost(res.data.listPost)
      // console.log(res.data.listPost)
      // setFilter(pagination)
      setCount(res.data.count)

    });
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
      {/* {currentUser==null ? ( */}
        <Feed>
          {posts.map(p =>
            <FeedPost posts={p}  />
          )}
        </Feed>
      {/* ) : (

        <Feed>

          {posts.filter((p) => currentUser === p.username)
            .map(post =>
              <FeedPost posts={post} action={likeHandler} />

            )}
        </Feed>

          ) */}

      {/* } */}

      <Pagination
        defaultActivePage={1}
        ellipsisItem={null}
        firstItem={null}
        lastItem={null}
        // totalPages={items.length}
        onPageChange={handlePageChange}
      >
      </Pagination>

    </>
  );
}

function FeedPost(props) {
  var timeAgo = moment(props.posts.created_date).fromNow()
  return (
    <div className="post">
      <div className="postWrapper">
        <div className="postTop">
          <div className="postTopLeft">
            <img className="postProfileImg" src={props.posts.ownerPost.avatar} alt="avatar" />
            <span className="postUsername">
              {props.posts.ownerPost.username}
              <span> created a post</span>
            </span>
            <span className="postDate">{timeAgo}</span>
          </div>
          <div className="postTopRight">
          </div>
        </div>
        <div className="postCenter">
          <span className="postText">{props.posts.content}</span>
          <img className="postImg" src={props.posts.image} alt="" />
        </div>
        <div className="postBottom">
          <div className="postBottomLeft">
            <img className="likeIcon" src="/assert/like.png" onClick={props.likeHandler} alt="" />
            <img className="likeIcon" src="/assert/heart.png" onClick={props.likeHandler} alt="" />
            {props.posts.likes.length === 0 ? (
              <span className="postLikeCounter">{props.posts.likes} </span>
            ) : (
              <span className="postLikeCounter">{props.posts.likes.length} </span>
            )}
          </div>
          <div className="postBottomRight">
            {props.posts.comments.length === 0 ? (

              <Modal
                id="cmtModal"
                trigger={<span>Comments</span>}>
                <Modal.Content>

                </Modal.Content>
                <Modal.Content>
                  <Form  >
                    <Form.TextArea placeholder="Write a public comment..." />
                    <Button size='mini' content='Add comment' labelPosition='left' icon='edit' primary />
                  </Form>
                </Modal.Content>
              </Modal>
            ) : (
              <span className="postCommentText">{props.posts.comments.length} Comments</span>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

function getComment(props) {
  var timeAgo = moment(props.comments.created_date).calendar()

  return (
    <Comment.Group minimal>
      <Header as='h3' dividing>
        Comments
      </Header>
      <Comment>
        <Comment.Avatar as='a' src={props.user.avatar} />
        <Comment.Content>
          <Comment.Author as='a'>{props.user.username}</Comment.Author>
          <Comment.Metadata>
            <span>{timeAgo}</span>
          </Comment.Metadata>
          <Comment.Text>How artistic!</Comment.Text>
          <Comment.Actions>
            <a>Reply</a>
          </Comment.Actions>
        </Comment.Content>
      </Comment>
    </Comment.Group>
  )

}

