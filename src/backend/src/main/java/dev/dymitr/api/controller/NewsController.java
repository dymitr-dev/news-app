package dev.dymitr.api.controller;

import dev.dymitr.api.config.AppProperties;
import dev.dymitr.api.model.NewsApiResponses;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final AppProperties appProperties;

    @ApiOperation(value = "Get news.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", defaultValue = "0", value = "Use this to page through the results if the total results found is greater than the page size."),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", defaultValue = "20", allowableValues = "range[1, 100]", value = "The number of results to return per page (request). 20 is the default, 100 is the maximum.")
    })
    @NewsApiResponses
    @RequestMapping(value = "{country}/{category}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNews(
            @ApiParam(
                    required = true,
                    value = "The 2-letter ISO 3166-1 code of the country you want to get headlines for.",
                    allowableValues = "ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu, id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk, th, tr, tw, ua, us, ve, za"
            )
            @PathVariable("country") String country,
            @ApiParam(
                    required = true,
                    value = "The category you want to get headlines for.",
                    allowableValues = "business, entertainment, general, health, science, sports, technology"
            )
            @PathVariable("category") String category,
            @PageableDefault(value = 20) @ApiIgnore Pageable pageable
    ) throws URISyntaxException, IOException {
        String url = new URIBuilder(appProperties.getNewsApiEndpoint())
                .addParameter("apiKey", appProperties.getNewsApiKey())
                .addParameter("country", country)
                .addParameter("category", category)
                .addParameter("pageSize", String.valueOf(pageable.getPageSize()))
                .addParameter("page", String.valueOf(pageable.getPageNumber()))
                .build().toString();

        log.info(url);

        HttpResponse response = Request.Get(url).execute().returnResponse();
        String body = EntityUtils.toString(response.getEntity());
        int code = response.getStatusLine().getStatusCode();

        return new ResponseEntity<>(body, HttpStatus.valueOf(code));
    }
}