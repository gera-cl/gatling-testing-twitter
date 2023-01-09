function formatStats(content) {
    let stats = [];

    let koPercent = 0;
    if (content.stats.numberOfRequests.total !== 0) {
        koPercent = (content.stats.numberOfRequests.ko * 100 / content.stats.numberOfRequests.total).toFixed(0);
    }

    stats.push(
        {
            name: content.name,
            type: content.type,
            numberOfRequests: content.stats.numberOfRequests,
            koPercent: `${koPercent}%`,
            percentiles1: content.stats.percentiles1.total,
            percentiles2: content.stats.percentiles2.total,
            percentiles3: content.stats.percentiles3.total,
            percentiles4: content.stats.percentiles4.total,
            minResponseTime: content.stats.minResponseTime.total,
            meanNumberOfRequestsPerSecond: content.stats.meanNumberOfRequestsPerSecond.total,
            maxResponseTime: content.stats.maxResponseTime.total,
            meanResponseTime: content.stats.meanResponseTime.total,
            standardDeviation: content.stats.standardDeviation.total,
            subContent: []
        }
    );

    if (content.type === 'GROUP') {
        for (const subContent in content.contents) {
            if (Object.prototype.hasOwnProperty.call(content.contents, subContent)) {
                if (content.name === 'All Requests') {
                    stats = stats.concat(formatStats(content.contents[subContent]))
                } else {
                    stats[stats.length - 1].subContent.push(formatStats(content.contents[subContent]));
                }
            }
        }
    }

    return stats;
}

module.exports = { formatStats };
