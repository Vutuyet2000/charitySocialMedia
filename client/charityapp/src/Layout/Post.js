import { useEffect } from 'react';
import { useState } from 'react';
import { useStore } from 'react-redux';
import { Container, Segment, Feed, Icon, Pagination, Image } from 'semantic-ui-react';
import API, { AuthAPI, endpoints } from '../API';
import moment from "moment"
import cookies from 'react-cookies'

import './Post.css'
export default function Post(props) {
  const [posts, setPost] = useState([])

  const [like, setLike] = useState(posts.likes)
  const [isLiked, setIsLiked] = useState(false)

  const likeHandler = () => {
    setLike(isLiked ? like - 1 : like + 1)
    setIsLiked(!isLiked)
  }

  const getPost = () => {
    AuthAPI.get(endpoints['post'], {
      headers: {
        'Authorization': `Bearer ${cookies.load('access_token')}`
      }
    }).then(res => {
      setPost(res.data.results)
      console.log(res.data.results)
    });
  }

  useEffect(() => {
    getPost();
  }, [])
  return (
    <>
      <Feed>
        {posts.map(p =>
          <FeedPost posts={p} action={likeHandler} />
        )}
      </Feed>

      <Pagination
        boundaryRange={0}
        defaultActivePage={1}
        ellipsisItem={null}
        firstItem={null}
        lastItem={null}
        siblingRange={1}
        totalPages={10}
      />

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
            <img className="postProfileImg" src={props.posts.user.avatar} alt="avatar" />
            <span className="postUsername">
              {props.posts.user.username}
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
            <span className="postLikeCounter">{props.posts.likes} </span>
          </div>
          <div className="postBottomRight">
            <span className="postCommentText">{props.posts.comments} Comments</span>
          </div>
        </div>
      </div>
    </div>

    // <Feed.Event className="detailPost" >
    //   <Feed.Label >
    //     <img src={props.posts.user.avatar} alt="avatar" />
    //   </Feed.Label>
    //   <Feed.Content>
    //     <Feed.Summary>
    //       <a>{props.posts.user.username}</a> posted
    //       <Feed.Date>
    //         {props.posts.created_date}
    //       </Feed.Date>
    //     </Feed.Summary>
    //     <Feed.Extra text>
    //       {props.posts.content}
    //     </Feed.Extra>
    //     <Feed.Extra images>
    //       <a>
    //         <img src={props.posts.image} alt="picture" />
    //       </a>
    //     </Feed.Extra>
    //     <Feed.Meta>
    //       <Feed.Like>
    //         <Icon name='like' />{props.posts.likes}
    //       </Feed.Like>
    //       <Feed.Like>
    //         <Icon name='comment' />{props.posts.comments}
    //       </Feed.Like>
    //     </Feed.Meta>
    //   </Feed.Content>
    // </Feed.Event>

  )
}