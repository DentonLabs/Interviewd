using System.Collections.Generic;

namespace Interviewd.Application.Dto
{
    public class InterviewDto
    {
        public string Id { get; set; }

        public CandidateDto Candidate { get; set; }

        public IEnumerable<QuestionDto> Questions { get; set; }
    }
}