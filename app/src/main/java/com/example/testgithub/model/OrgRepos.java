package com.example.testgithub.model;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = {"id"})
public class OrgRepos{

	@SerializedName("id")
	private int id;

	@SerializedName("full_name")
    @Expose
	private String fullName;

	@SerializedName("clone_url")
    @Expose
	private String cloneUrl;

	@SerializedName("name")
    @Expose
	private String name;


	@SerializedName("description")
    @Expose
	private String description;

	@SerializedName("created_at")
    @Expose
	private String createdAt;


	@SerializedName("updated_at")
    @Expose
	private String updatedAt;

	@SerializedName("git_url")
    @Expose
	private String gitUrl;


	@SerializedName("downloads_url")
	private String downloadsUrl;


	@SerializedName("homepage")
    @Expose
	private String homepage;

	@SerializedName("language")
	@Expose
	private String language;

    @SerializedName("forks_count")
	@Expose
	private Integer forksCount;

    @SerializedName("watchers")
    @Expose
    private Integer watchers;

    @SerializedName("stargazers_count")
    @Expose
    private Integer stargazersCount;

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCloneUrl() {
		return cloneUrl;
	}

	public void setCloneUrl(String cloneUrl) {
		this.cloneUrl = cloneUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getDownloadsUrl() {
		return downloadsUrl;
	}

	public void setDownloadsUrl(String downloadsUrl) {
		this.downloadsUrl = downloadsUrl;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
}