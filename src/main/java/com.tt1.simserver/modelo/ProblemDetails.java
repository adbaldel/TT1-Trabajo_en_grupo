package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.Objects;

/**
 *
 */
@JsonTypeName("ProblemDetails")
public class ProblemDetails extends HashMap<String, Object> {
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;


    /**
     *
     * @param type
     * @return
     */
    public ProblemDetails type(String type) {
        this.type = type;
        return this;
    }


    /**
     *
     * @return
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @param title
     * @return
     */
    public ProblemDetails title(String title) {
        this.title = title;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @param status
     * @return
     */
    public ProblemDetails status(Integer status) {
        this.status = status;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @param detail
     * @return
     */
    public ProblemDetails detail(String detail) {
        this.detail = detail;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     */
    @JsonProperty("detail")
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     *
     * @param instance
     * @return
     */
    public ProblemDetails instance(String instance) {
        this.instance = instance;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("instance")
    public String getInstance() {
        return instance;
    }

    /**
     *
     * @param instance
     */
    @JsonProperty("instance")
    public void setInstance(String instance) {
        this.instance = instance;
    }

    /**
     *
     * @param o object to be compared for equality with this map
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, title, status, detail, instance, super.hashCode());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
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
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

