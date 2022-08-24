package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.dto.UsersFormDto;
import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.oauth.OAuthTokenxRequest;
import com.monkeyk.os.oauth.token.OAuthTokenHandleDispatcher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2015/7/3
 * <p/>
 * URL: oauth/token
 *
 * @author Shengzhao Li
 */
@Api("token")
@Controller
@RequestMapping("oauth/")
public class OauthTokenController {


    /**
     * Handle grant_types as follows:
     * <p/>
     * grant_type=authorization_code
     * grant_type=password
     * grant_type=refresh_token
     * grant_type=client_credentials
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws OAuthSystemException
     */
    @ApiOperation("获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name="grant_type",value="授权类型",required=true,paramType="form"),
            @ApiImplicitParam(name="username",value="username",required=true,paramType="form"),
            @ApiImplicitParam(name="password",value="password",required=true,paramType="form"),
            @ApiImplicitParam(name="client_id",value="client_id",required=true,paramType="form",dataType="String"),
            @ApiImplicitParam(name="client_secret",value="client_secret",required=true,paramType="form",dataType="String"),
            @ApiImplicitParam(name="scope",value="scope",required=true,paramType="form",dataType="String")
    })
    @RequestMapping("token")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException {
        try {
            OAuthTokenxRequest tokenRequest = new OAuthTokenxRequest(request);

            OAuthTokenHandleDispatcher tokenHandleDispatcher = new OAuthTokenHandleDispatcher(tokenRequest, response);
            tokenHandleDispatcher.dispatch();

        } catch (OAuthProblemException e) {
            //exception
            OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }

    }


    @RequestMapping(value = "oauth_test.html", method = RequestMethod.GET)
    public String plusUser(Model model) {

        return "oauth_test.html";
    }
}
