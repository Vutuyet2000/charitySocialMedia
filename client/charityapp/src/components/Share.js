import "./Share.css";
import { Button, Icon, Image, Label, Modal, TextArea } from 'semantic-ui-react'
import { useStore } from "react-redux";
import getUser from "../getUser";
import { useEffect, useState } from "react";
import { AuthAPI, endpoints } from "../API";
import useFormValidation from "../useFormValidation";
import cookies from 'react-cookies'

export default function Share() {
  const store = useStore()
  let { user } = getUser(store)
  const [open, setOpen] = useState(true)
  const [share, setShare] = useState({
    content: ""
  })

  const [picture, setPicture] = useState(null)
  const [image, setImage] = useState(null)
  const [isUploaded, setIsUploaded] = useState(false)

  // hàm reload lại các state
  function reload() {
    setIsUploaded(false);
    setPicture(null);
    setShare({ content: "" })
  }

  // hàm load ảnh và preview ảnh được load
  function handleImageChange(e) {
    setImage(e.target.files[0])
    if (e.target.files && e.target.files[0]) {
      let reader = new FileReader();

      reader.onload = function (e) {
        setPicture(e.target.result);
        setIsUploaded(true);

      };
      reader.readAsDataURL(e.target.files[0]);
    }
  }

  // hàm thay đổi giá trị của content
  function handleChange(event) {
    setShare({
      ...share,
      [event.target.name]: event.target.value
    })
  };

  // hàm để thực hiện gọi API của post bình thường
  function handlePost(event) {
    const formData = new FormData()
    for (let k in share)
      formData.append(k, share[k])
    formData.append('image', image)
    AuthAPI.post(endpoints['post'], formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': `Bearer ${cookies.load('access_token')}`
      }
    }).then(res => {
      console.log(res)
    }).catch(err => {
      console.error(err)
    })
    // Chặn trang tự reload theo mặc định để hàm async trên kia chạy xong
    event.preventDefault();
    reload()
  }

  // hàm gọi API của post đấu giá
  function handleAuction(event) {
    const formData = new FormData()
    for (let k in share)
      formData.append(k, share[k])
    formData.append('image', image)
    AuthAPI.post(endpoints['auction'], formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': `Bearer ${cookies.load('access_token')}`
      }
    }).then(res => {
      console.log(res)
    }).catch(err => {
      console.error(err)
    })
    // Chặn trang tự reload theo mặc định để hàm async trên kia chạy xong
    event.preventDefault();
    reload()

  }

  return (
    <div className="share">
      <div className="shareWrapper">
        <div className="shareTop">
          <img
            className="shareProfileImg"
            src={user.avatar}
            alt="avatar" />
          <TextArea id="content"
            placeholder="What's in your mind?"
            className="shareInput"
            name="content"
          />

        </div>
        <hr className="shareHr" />
        <div className="shareBottom">
          <div className="shareOptions" >
            <label htmlFor="file" className="shareOption">
              <Icon color="green" size="large" name="images" />
              <span itemType="file" className="shareOptionText">Photo</span>

              {!isUploaded ? (
                <>
                  <input
                    style={{ display: "none" }}
                    type="file"
                    id="file"
                    name="image"
                    accept=".png,.jpeg,.jpg"
                    onChange={handleImageChange}
                  />

                </>
              ) : (
                <>
                  <Modal
                    onClose={() => setOpen(false)}
                    onOpen={() => setOpen(true)}
                    open={open} id="modal_upload">
                    <Modal.Header style={{ textAlign: 'center' }} >Create Post</Modal.Header>
                    <div className="shareTop">
                      <Image avatar
                        spaced='right'
                        style={{ marginLeft: '17px', marginTop: '13px' }}
                        src={user.avatar} />
                      <Label
                        basic
                        circular
                        color="black"
                        style={{ marginTop: '12px' }}>
                        {user.username}

                      </Label>
                    </div>
                    <Modal.Content>
                      <TextArea id="content"
                        placeholder="What's in your mind?"
                        className="shareInput"
                        name="content"
                        value={share.content}
                        onChange={handleChange} />
                    </Modal.Content>
                    <Modal.Content id="upload_content" image>
                      <img
                        id="uploaded-image"
                        src={picture}
                        draggable={false}
                        alt="uploaded-img"
                        onChange={handleImageChange}
                      />
                      <Icon
                  className="close-icon"
                  name="close"
                  size="large"
                  alt="CloseIcon"
                  onClick={() => {
                    setIsUploaded(false);
                    
                  }}
                />

                    </Modal.Content>
                    {share.content.trim() !== "" ? (
                      <Modal.Actions>
                        <Button onClick={() => reload()} >Cancel</Button>
                        <Button
                          primary
                          onClick={handlePost}>
                          Post
                        </Button>
                        <Button id="auctionID"
                          positive
                          onClick={handleAuction}
                        >
                          Auction
                        </Button>

                      </Modal.Actions>
                    ) : (
                      <Modal.Actions>
                        <Button onClick={() => reload()}>Cancel</Button>
                        <Button primary disabled>
                          Post
                        </Button>
                        <Button disabled positive >Auction</Button>

                      </Modal.Actions>
                    )

                    }

                  </Modal>


                </>
              )}
            </label>
            <div className="shareOption">

              <Icon color="blue" size="large" name="tags" />
              <span className="shareOptionText">Tags</span>
            </div>
            <div className="shareOption">
              <Icon color="red" size="large" name="map marker alternate" />
              <span className="shareOptionText">Location</span>
            </div>
            <div className="shareOption">
              <Icon color="yellow" size="large" name="smile outline" />
              <span className="shareOptionText">Feelings</span>
            </div>
          </div>
          <Button id="postID"    className="shareButton" color="blue">Post</Button>
        </div>
      </div>
    </div>
  );
}
