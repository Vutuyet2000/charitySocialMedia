
import { Comment, Header, Form, Pagination } from 'semantic-ui-react';
import moment from "moment"
import { useEffect } from 'react';
import { useState } from 'react';
import { AuthAPI, endpoints } from '../API';

export default function Comments(props){
    var timeAgo = moment(props.comments.created_date).calendar()
    // const getComment = () => {
    //     AuthAPI.get(`${endpoints['post']}?page=${page}`, {
    //       headers: {
    //         'Authorization': `Bearer ${cookies.load('access_token')}`
    //       }
    
    //     }).then(res => {
    //       console.log(res.data.listPost)
    //     });
    //   }
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