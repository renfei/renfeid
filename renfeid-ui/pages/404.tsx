import Head from 'next/head'
import {Button, Result} from 'antd';
import Layout from "../components/layout";
import styles from "../styles/Home.module.css";

const Error404Page = () => {
    return (
        <>
            <div className={styles.container}>
                <Head>
                    <title>404 - Page Not Found</title>
                    <meta name="description" content="Generated by create next app"/>
                    <link rel="icon" href="/favicon.ico"/>
                </Head>

                <div style={{backgroundColor: '#ffffff'}}>
                    <Result
                        status="404"
                        title="404"
                        subTitle="Sorry, the page you visited does not exist."
                        extra={<Button type="primary">Back Home</Button>}
                    />
                </div>
            </div>
        </>
    )
}

Error404Page.getLayout = (page: any) => {
    return (
        <Layout>
            {page}
        </Layout>
    )
}

export default Error404Page