import LeftMenu from "../components/LeftMenu";
import Feed from "../Layout/Feed";
import Header from "../Layout/Header";

export default function Home(){
    return(
        <>
            <Header /> 
            <LeftMenu/>
            <Feed/>
        </>
    );
}