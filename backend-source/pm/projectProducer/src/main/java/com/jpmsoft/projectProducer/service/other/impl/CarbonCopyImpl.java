package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.jpmsoft.projectProducer.mapper.other.CarbonCopyMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.CarbonCopy;
import com.jpmsoft.projectProducer.service.other.CarbonCopyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarbonCopyImpl extends ServiceImpl<CarbonCopyMapper, CarbonCopy> implements CarbonCopyService {

}
