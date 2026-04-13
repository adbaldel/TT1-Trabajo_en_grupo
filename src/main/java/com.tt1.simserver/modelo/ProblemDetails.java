package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Objects;


@JsonTypeName("ProblemDetails")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-04-13T19:26:41.786837200+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
public class ProblemDetails extends HashMap<String, Object>
{
    private @Valid String type;
    private @Valid String title;
    private @Valid Integer status;
    private @Valid String detail;
    private @Valid String instance;

    /**
     *
     **/
    public ProblemDetails type(String type)
    {
        this.type = type;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("type")
    public String getType()
    {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     *
     **/
    public ProblemDetails title(String title)
    {
        this.title = title;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("title")
    public String getTitle()
    {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     *
     **/
    public ProblemDetails status(Integer status)
    {
        this.status = status;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("status")
    public Integer getStatus()
    {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    /**
     *
     **/
    public ProblemDetails detail(String detail)
    {
        this.detail = detail;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("detail")
    public String getDetail()
    {
        return detail;
    }

    @JsonProperty("detail")
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     *
     **/
    public ProblemDetails instance(String instance)
    {
        this.instance = instance;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("instance")
    public String getInstance()
    {
        return instance;
    }

    @JsonProperty("instance")
    public void setInstance(String instance)
    {
        this.instance = instance;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ProblemDetails problemDetails = (ProblemDetails) o;
        return Objects.equals(this.type, problemDetails.type) &&
                Objects.equals(this.title, problemDetails.title) &&
                Objects.equals(this.status, problemDetails.status) &&
                Objects.equals(this.detail, problemDetails.detail) &&
                Objects.equals(this.instance, problemDetails.instance) &&
                super.equals(o);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(type, title, status, detail, instance, super.hashCode());
    }

    @Override
    public String toString()
    {
        String sb = "class ProblemDetails {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "    type: " + toIndentedString(type) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
                "    detail: " + toIndentedString(detail) + "\n" +
                "    instance: " + toIndentedString(instance) + "\n" +
                "}";
        return sb;
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o)
    {
        if (o == null)
        {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


}

