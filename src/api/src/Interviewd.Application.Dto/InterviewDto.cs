using System.Collections.Generic;
using Interviewd.Common;

namespace Interviewd.Application.Dto
{
    public class InterviewDto : IIdentifiable
    {
        public string Id { get; set; }

        public CandidateDto Candidate { get; set; }

        public IEnumerable<QuestionDto> Questions { get; set; }
    }
}