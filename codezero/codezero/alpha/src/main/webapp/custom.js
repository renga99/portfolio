

var observer = new MutationSummary({
  callback: handleHTweetChanges,
  queries: [{ all: true }]
});


function handleHTweetChanges(summaries) {
	console.log(summaries);
}
