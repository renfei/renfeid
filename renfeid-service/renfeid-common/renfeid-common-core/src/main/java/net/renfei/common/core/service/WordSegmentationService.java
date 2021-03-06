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
package net.renfei.common.core.service;

import java.util.List;

/**
 * 分词服务
 *
 * @author renfei
 */
public interface WordSegmentationService {
    /**
     * 从内容中提取10个关键字，基于词频
     *
     * @param content 内容
     * @return
     */
    List<String> extractKeywords(String content);

    /**
     * 从内容中提取关键字，基于词频
     *
     * @param content  内容
     * @param quantity 每个词最小字数
     * @param number   获取关键词数量
     * @return
     */
    List<String> extractKeywords(String content, Integer quantity, Integer number);

    /**
     * 对内容进行分词
     *
     * @param content  内容
     * @param quantity 单词最小字数
     * @return
     */
    List<String> wordSegmentation(String content, Integer quantity);
}
