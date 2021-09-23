import { useStore } from 'react-redux';
import getUser from '../getUser'
import '../components/Profile.css'
import Header from '../Layout/Header';
import Post from '../Layout/Post';
import cookies from 'react-cookies'

export default function Profile() {
    const store = useStore()

    let { user } = getUser(store)

    if (cookies.load("user") != null)
        user = cookies.load("user")

    return (
        <>
            <Header />
            <div class="container db-social">
                <div class="jumbotron jumbotron-fluid"></div>
                <div class="container-fluid">
                    <div class="row justify-content-center">
                        <div class="col-xl-11">
                            <div class="widget head-profile ">
                                <div class="widget-body pb-0">
                                    <div class="row d-flex align-items-center">
                                        
                                        <div class="col-xl-4 col-md-4 d-flex justify-content-center">
                                            <div class="image-default">
                                                <img class="rounded-circle" src={user.avatar} alt="..." />
                                </div>
                                                <div class="infos">
                                                    <h2>{user.username}</h2>
                                                    <div class="location">Las Vegas, Nevada, U.S.</div>
                                                </div>
                                            </div>
                                            <div class="col-xl-4 col-md-4 d-flex justify-content-lg-end justify-content-md-end justify-content-center">
                                                <div class="follow">
                                                    <div class="actions dark">
                                                        <div class="dropdown">
                                                            <button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="dropdown-toggle">
                                                                <i class="la la-ellipsis-h"></i>
                                                            </button>
                                                            <div class="dropdown-menu" x-placement="bottom-start" style={{display: "none", 
                                                            position: "absolute",
                                                             willChange: "transform",
                                                              top: "0px",
                                                              left: "0px",
                                                              transform: "translate3d(0px, 35px, 0px)"
                                                              }}>
                                                                {/* <a href="#" class="dropdown-item">
                                                                    Report
                                                                </a>
                                                                <a href="#" class="dropdown-item">
                                                                    Block
                                                                </a> */}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <Post ownerPost={user} />
        </>
            )
}
