// @ts-ignore
/* eslint-disable */

declare namespace API {
    // 秘钥交互对象
    interface SecretKey {
        uuid: string,
        publicKey: string,
        privateKey: string,
    }

    interface SecretValue {
        secretKey: SecretKey,
        value: string,
    }

    // 加密后对象
    interface EncryptionVo {
        uuid: string,
        ciphertext: string,
    }

    // 登录请求对象
    interface SignInAo {
        userName: string,
        password: string,
        tOtp?: string,
        keyUuid: string,
        useVerCode?: boolean,
        plainPassword: string,
    }

    interface SignInVo {
        accessToken: string,
        ucScript?: string,
    }

    interface APIResult<T> {
        code: number,
        message: string,
        timestamp: number,
        signature: string,
        nonce: string,
        data?: T
    }

    // 列表数据对象
    interface ListData<T> {
        pageNum: number,
        pageSize: number,
        startRow: number,
        endRow: number,
        total: number,
        pages: number,
        data: T[],
    }

    // 文章内容对象
    interface PostVo {
        id: string,
        categoryId: string,
        postAuthor: string,
        postDate: string,
        postStatus: string,
        postViews: number,
        commentStatus: string,
        thumbsUp: number,
        thumbsDown: number,
        avgViews: number,
        avgComment: number,
        pageRank: number,
        secretLevel: string,
        featuredImage: string,
        postTitle: string,
        postKeyword: string,
        postExcerpt: string,
        postContent: string,
        sourceName: string,
        sourceUrl: string,
        original: true,
    }

    // 后台内容管理对象
    interface DashPost {
        id: string,
        categoryId: string,
        postAuthor: string,
        postDate: string,
        postStatus: string,
        postViews: number,
        commentStatus: string,
        postPassword: string,
        postModified: string,
        postModifiedUser: string,
        postParent: number,
        versionNumber: number,
        thumbsUp: number,
        thumbsDown: number,
        avgViews: number,
        avgComment: number,
        pageRank: number,
        secretLevel: string,
        isOriginal: boolean,
        featuredImage: string,
        postTitle: string,
        postKeyword: string,
        postExcerpt: string,
        postContent: string,
        sourceName: string,
        sourceUrl: string,
        authorUsername: string,
        modifiedUsername: string,
    }

    interface PostCategory {
        id: string,
        enName: string,
        zhName: string,
        secretLevel: string,
    }

    // 文件上传响应对象
    interface UploadObjectVo {
        location: string,
        alt: string,
    }

    // 评论树
    interface CommentTree {
        id: string,
        addtime: string,
        isOwner: boolean,
        author: string,
        authorUrl: string,
        authorAddress: string,
        content: string,
        children?: CommentTree[]
    }

    // 用户登录信息
    interface UserInfo {
        email?: string
        emailVerified: boolean
        firstName?: string
        id: string
        lastName?: string
        phone?: string
        phoneVerified: boolean
        registrationDate: string
        registrationIp: string
        secretLevel: string
        username: string
        uuid: string
    }

    interface AntdSelectOption {
        label: string,
        value: string,
    }

    interface TableListParams {
        pagination?: TablePaginationConfig
        sorter?: SorterResult<any> | SorterResult<any>[]
        total?: number
        sortField?: string
        sortOrder?: string
    }

    interface RoleDetail {
        id: string,
        roleName: string,
        roleDescribe: string,
        addTime: string,
        updateTime: string,
        builtInRole: boolean,
        extendJson?: string,
    }

    interface UserDetail {
        id: string,
        uuid: string,
        username: string,
        email: string,
        emailVerified: boolean,
        phone?: string,
        phoneVerified: boolean,
        registrationDate: string,
        password: string,
        totp?: string,
        registrationIp: string,
        trialErrorTimes?: number,
        lockTime: string,
        secretLevel: string,
        builtInUser: boolean,
        passwordExpirationTime?: string,
        locked: boolean,
        enabled: boolean,
        lastName?: string,
        firstName?: string,
        keyUuid: string,
        roleDetailList?: RoleDetail[]
    }

    interface ResetPasswordAo {
        password: string,
        tOtp: string,
        keyUuid: string,
        verCode: string,
    }

    interface Authority {
        authorityType: string,
        targetId: string
    }

    interface RoleDetail {
        id: string,
        roleName: string,
        roleDescribe?: string,
        addTime: string,
        updateTime?: string,
        extendJson?: string,
        authorityList?: Authority[]
    }

    interface SystemApi {
        id: string,
        urlPath: string,
        methodName: string,
        summary: string,
        description: string,
    }

    interface MenuTree {
        id: string,
        pid?: string,
        menuName: string,
        menuHref: string,
        menuIcon?: string,
        menuTarget?: string,
        menuClass?: string,
        menuTitle?: string,
        menuOnclick?: string,
        menuOrder?: string,
        enable?: string,
        addTime?: string,
        updateTime?: string,
        menuCss?: string,
        extendJson?: string,
        child?: MenuTree[],
    }
}