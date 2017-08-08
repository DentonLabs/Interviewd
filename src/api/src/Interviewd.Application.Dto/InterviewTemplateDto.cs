using System.Collections.Generic;

namespace Interviewd.Application.Dto
{
    public class InterviewTemplateDto
    {
        public string Id { get; set; }

        public string Name { get; set; }

        public IEnumerable<string> QuestionIds { get; set; }
    }
}