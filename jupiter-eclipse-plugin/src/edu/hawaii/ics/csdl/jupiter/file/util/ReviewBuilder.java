package edu.hawaii.ics.csdl.jupiter.file.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.hawaii.ics.csdl.jupiter.file.property.CreationDate;
import edu.hawaii.ics.csdl.jupiter.file.property.Entry;
import edu.hawaii.ics.csdl.jupiter.file.property.FieldItem;
import edu.hawaii.ics.csdl.jupiter.file.property.Files;
import edu.hawaii.ics.csdl.jupiter.file.property.Filter;
import edu.hawaii.ics.csdl.jupiter.file.property.Filters;
import edu.hawaii.ics.csdl.jupiter.file.property.Phase;
import edu.hawaii.ics.csdl.jupiter.file.property.Review;
import edu.hawaii.ics.csdl.jupiter.file.property.Reviewers;
import edu.hawaii.ics.csdl.jupiter.file.serializers.ReviewSerializer;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;

public class ReviewBuilder {

	public static Review build(Review review) {
		if (review == null) {
			return null;
		}

		Review copiedReview = new Review();
		copiedReview.setAuthor(review.getAuthor());
		copiedReview.setDescription(review.getDescription());
		copiedReview.setDirectory(review.getDirectory());
		copiedReview.setId(review.getId());

		CreationDate creationDate = review.getCreationDate();
		if (creationDate != null) {
			CreationDate copiedCreationDate = new CreationDate();
			copiedCreationDate.setFormat(creationDate.getFormat());
			copiedCreationDate.setValue(creationDate.getValue());
			copiedReview.setCreationDate(copiedCreationDate);
		}

		Reviewers reviewers = review.getReviewers();
		if (reviewers != null) {
			Reviewers copiedReviewers = new Reviewers();

			List<Entry> entryList = reviewers.getEntries();
			for (Entry entry : entryList) {
				Entry copiedReviewersEntry = new Entry();
				copiedReviewersEntry.setId(entry.getId());
				copiedReviewersEntry.setName(entry.getName());
				copiedReviewers.getEntries().add(copiedReviewersEntry);
			}
			copiedReview.setReviewers(copiedReviewers);
		}

		Files files = review.getFiles();
		if (files != null) {
			Files copiedFiles = new Files();

			List<Entry> entryList = files.getEntries();
			for (Entry entry : entryList) {
				Entry copiedFilesEntry = new Entry();
				copiedFilesEntry.setName(entry.getName());
				copiedFiles.getEntries().add(copiedFilesEntry);
			}
			copiedReview.setFiles(copiedFiles);
		}

		List<FieldItem> fieldItems = review.getFieldItems();
		if (fieldItems != null) {
			List<FieldItem> copiedFieldItems = new ArrayList<FieldItem>(
					fieldItems);
			copiedReview.setFieldItems(copiedFieldItems);
		}

		Filters filters = review.getFilters();
		if (filters != null) {
			Filters copiedFilters = new Filters();

			List<Phase> phaseList = filters.getPhases();
			for (Phase phase : phaseList) {
				Phase copiedPhase = new Phase();
				copiedPhase.setName(phase.getName());
				copiedPhase.setEnabled(phase.isEnabled());

				// filter objects
				List<Filter> filterList = phase.getFilters();
				for (Filter filter : filterList) {
					Filter copiedFilter = new Filter();
					copiedFilter.setName(filter.getName());
					copiedFilter.setValue(filter.getValue());
					copiedFilter.setEnabled(filter.isEnabled());
					copiedPhase.getFilters().add(copiedFilter);
				}
				copiedFilters.getPhases().add(copiedPhase);
			}
			copiedReview.setFilters(copiedFilters);
		}

		return copiedReview;

	}

	public static edu.hawaii.ics.csdl.jupiter.file.review.Review createReviewFromModel(
			ReviewId reviewId, ReviewIssueModel model, File xmlFile) {
		edu.hawaii.ics.csdl.jupiter.file.review.Review review = new edu.hawaii.ics.csdl.jupiter.file.review.Review();
		review.setId(reviewId.getReviewId());
		Map<String, ReviewIssue> map = model.getReviewIssueMap();
		Collection<ReviewIssue> reviewIssues = map.values();
		List<edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue> issues = new ArrayList<edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue>(
				reviewIssues.size());

		for (ReviewIssue reviewIssue : reviewIssues) {
			if (xmlFile.equals(reviewIssue.getReviewIFile().getLocation()
					.toFile())) {
				edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue xmlReviewIssue = ReviewIssueBuilder
						.createXmlReviewIssue(reviewIssue,
								ReviewSerializer.DATE_FORMAT_PATTERN,
								ReviewSerializer.DATE_FORMATTER);
				issues.add(xmlReviewIssue);
			}
		}
		review.setReviewIssues(issues);
		return review;
	}

}
