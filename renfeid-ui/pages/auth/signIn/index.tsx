import Head from 'next/head'
import Link from 'next/link'
import Image from 'next/image'
import {useRouter} from 'next/router'
import {Col, Row, Button, Typography, Divider, Card, Comment, List, Tooltip, Space, Form, Input} from 'antd'
import Layout from '../../../components/layout'
import styles from '../../../styles/auth/SignIn.module.css'
import {
    LockOutlined,
    UserOutlined,
    KeyOutlined,
} from '@ant-design/icons';
import SignInAo = API.SignInAo;

const {Title} = Typography;

let showMFA = false;

const signInSubmit = (values: SignInAo) => {
    console.warn(values)
}

const SignInPage = () => {
    const [form] = Form.useForm();
    const router = useRouter()
    const {redirect} = router.query

    return (
        <>
            <div>
                <Head>
                    <title>登录</title>
                    <meta name="description" content="Generated by create next app"/>
                    <link rel="icon" href="/favicon.ico"/>
                </Head>

                <main style={{backgroundColor: '#ffffff', padding: '60px 0'}}>
                    <section className={"renfeid-content"}>
                        <div className={styles.signInForm}>
                            <Row>
                                <Col md={15} sm={12} xs={24}
                                     style={{padding: '20px 20px 20px 0', display: 'flex', justifyContent: 'center'}}>
                                    <Image
                                        src={"/image/hire_re_gn5j.svg"}
                                        width={898.09814}
                                        height={398.74219}
                                    />
                                </Col>
                                <Col md={9} sm={12} xs={24}>
                                    <Title level={3} style={{fontWeight: '100'}}>欢迎回来！</Title>
                                    <p style={{color: 'rgba(0,0,0,0.6)', fontSize: '12px'}}>
                                        无论您何时归来，我们都在这里等着您。
                                    </p>
                                    <Form
                                        form={form}
                                        name="SignInForm"
                                        initialValues={{remember: true}}
                                        autoComplete="off"
                                        onFinish={async (values) => {
                                            await signInSubmit(values)
                                        }}
                                    >
                                        <Form.Item
                                            name="username"
                                            rules={[{required: true, message: '请输入您的账号！'}]}
                                        >
                                            <Input
                                                placeholder="账号"
                                                prefix={<UserOutlined/>}
                                            />
                                        </Form.Item>

                                        <Form.Item
                                            name="password"
                                            rules={[{required: true, message: '请输入您的密码！'}]}
                                        >
                                            <Input.Password
                                                placeholder="密码"
                                                prefix={<LockOutlined/>}
                                            />
                                        </Form.Item>

                                        {
                                            showMFA ? (
                                                <Form.Item
                                                    name="mfa"
                                                    rules={[{required: false, message: 'Please input your username!'}]}
                                                >
                                                    <Input
                                                        placeholder="MFA"
                                                        showCount maxLength={6}
                                                        prefix={<KeyOutlined/>}
                                                    />
                                                </Form.Item>
                                            ) : ''
                                        }

                                        <Form.Item>
                                            <Row>
                                                <Col style={{textAlign: 'right'}}>
                                                    <Link href={"/"}>忘记密码？</Link>
                                                </Col>
                                            </Row>
                                        </Form.Item>

                                        <Form.Item shouldUpdate>
                                            {() => (
                                                <Space>
                                                    <Button
                                                        type="primary"
                                                        shape="round"
                                                        htmlType="submit"
                                                        disabled={
                                                            !form.isFieldsTouched(true) ||
                                                            !!form.getFieldsError().filter(({errors}) => errors.length).length
                                                        }
                                                    >
                                                        立即登录
                                                    </Button>
                                                    <Button shape="round" href="/auth/signUp">
                                                        注册账号
                                                    </Button>
                                                </Space>
                                            )}

                                        </Form.Item>
                                    </Form>
                                </Col>
                            </Row>
                        </div>
                    </section>
                </main>
            </div>
        </>
    )
}

SignInPage.getLayout = (page: any) => {
    return (
        <Layout>
            {page}
        </Layout>
    )
}

export default SignInPage