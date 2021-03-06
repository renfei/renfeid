/*
 *   Copyright 2022 RenFei(i@renfei.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.renfei.server.controller.dash.uaa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.renfei.common.api.constant.APIResult;
import net.renfei.common.api.entity.ListData;
import net.renfei.common.core.annotation.OperationLog;
import net.renfei.common.core.entity.SystemTypeEnum;
import net.renfei.server.controller.AbstractController;
import net.renfei.uaa.api.SystemApiService;
import net.renfei.uaa.api.entity.SystemApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统API接口列表
 *
 * @author renfei
 */
@RestController
@RequestMapping("/_/api/uaa")
@Tag(name = "系统API接口列表", description = "系统API接口列表")
public class SystemApiController extends AbstractController {
    private final SystemApiService systemApiService;

    public SystemApiController(SystemApiService systemApiService) {
        this.systemApiService = systemApiService;
    }

    @GetMapping("system/api")
    @Operation(summary = "查询系统API接口列表", tags = {"系统API接口列表"},
            parameters = {
                    @Parameter(name = "url", description = "API地址"),
                    @Parameter(name = "pages", description = "页码"),
                    @Parameter(name = "rows", description = "每页数据量")
            }, description = "这里注册着系统全部API接口地址，每次系统新增接口以后由专业运维人员去数据库中添加，所以此资源不提供增删改")
    @OperationLog(module = SystemTypeEnum.SYS_ROLE, desc = "查询系统API列表")
    public APIResult<ListData<SystemApi>> querySystemApi(@RequestParam(value = "url", required = false) String url,
                                                         @RequestParam(value = "pages", required = false) Integer pages,
                                                         @RequestParam(value = "rows", required = false) Integer rows) {
        return systemApiService.allSystemApiList(url, pages == null ? 1 : pages, rows == null ? 1 : rows);
    }
}
