import Share from "../components/Share";
import "./Feed.css";
import Post from "./Post";
export default function Feed() {
    return (
      <div className="feed">
        <div className="feedWrapper">
          <Share />
            {/* <Post key={user.id} post={p} /> */}
          <Post />
        </div>
      </div>
    );
  }